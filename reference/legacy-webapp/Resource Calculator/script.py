# --- LATEST VERSION: FOLDER METHOD BUNDLER ---
# Validated: Maps items to local file paths (no stitching).
# Feature Added: Automatically removes alphabetical sorting from template to preserve JSON order.
# Requires 'item_images' folder to be present next to 1Resource Calculator.html.

import os
import json
import re
import zipfile

# --- CONFIGURATION ---
IMAGES_DIR = 'item_images'              # Folder containing individual item images
ITEMS_JSON_FILE = 'final_items_list_survival.json' # UPDATED: Now uses the survival enriched list
OUTPUT_MAP_FILE = 'item_map.json'       # Mapping of item -> filename
OUTPUT_HTML_FILE = '1Resource Calculator.html'
TEMPLATE_FILE = 'template.html'

def normalize_name(name):
    """
    Normalizes string for matching.
    """
    name = os.path.splitext(name)[0]
    # Remove zzzzz-numbers prefix
    name = re.sub(r'^z+-\d+-?', '', name, flags=re.IGNORECASE)
    name = name.replace('minecraft:', '').replace('item_', '').replace('block_', '')
    name = re.sub(r'[^a-zA-Z0-9]', '', name)
    return name.lower()

def load_json(path):
    with open(path, 'r') as f: return json.load(f)

def generate_standalone_html(items_list, map_data):
    print("\n--- Generating Web App ---")
    if os.path.exists(TEMPLATE_FILE):
        with open(TEMPLATE_FILE, 'r', encoding='utf-8') as f: html_template = f.read()
    else:
        print(f"CRITICAL ERROR: '{TEMPLATE_FILE}' not found!")
        return

    print("Injecting data...")
    items_json = json.dumps(items_list)
    map_json = json.dumps(map_data)
    
    # --- ORDER FIX ---
    # Automatically remove the array sorting line from the template logic
    # so the icons appear in the exact order of the JSON list.
    html_template = re.sub(r'itemsArray\.sort\(.*?\);', '// Sorting removed by build script to preserve JSON order', html_template, flags=re.DOTALL)
    
    # Replace Data Placeholders
    final_html = html_template.replace('ITEMS_JSON_PLACEHOLDER', items_json)
    final_html = final_html.replace('MAP_JSON_PLACEHOLDER', map_json)
    
    # Remove Base64 placeholders if they exist in the template (cleanup)
    final_html = final_html.replace('data:MIME_TYPE_PLACEHOLDER;base64,BASE64_PLACEHOLDER', '') 

    with open(OUTPUT_HTML_FILE, 'w', encoding='utf-8') as f: f.write(final_html)
    print(f"Success! Open '{OUTPUT_HTML_FILE}'")
    print(f"IMPORTANT: Ensure the '{IMAGES_DIR}' folder is in the same directory as the HTML file.")

def main():
    print("--- Minecraft Item Mapper (Folder Method) ---")
    
    # Check for folder first, then zip
    if not os.path.exists(IMAGES_DIR):
        zip_path = IMAGES_DIR + ".zip"
        if os.path.exists(zip_path):
            print(f"Found '{zip_path}'. Extracting...")
            with zipfile.ZipFile(zip_path, 'r') as zip_ref:
                zip_ref.extractall(".")
        else:
            print(f"Error: Folder '{IMAGES_DIR}' not found.")
            return

    try:
        items_list = load_json(ITEMS_JSON_FILE)
        items_list = items_list.get('items', []) if isinstance(items_list, dict) else items_list
    except Exception as e:
        print(f"Error loading JSON: {e}")
        return

    # 1. Index Images
    image_files = {}
    print(f"Scanning '{IMAGES_DIR}' for images...")
    for root, dirs, files in os.walk(IMAGES_DIR):
        for f in files:
            if f.lower().endswith(('.png', '.jpg', '.jpeg')):
                norm = normalize_name(f)
                # Store relative path (replace backslashes for web compatibility)
                rel_path = os.path.join(IMAGES_DIR, f).replace("\\", "/")
                image_files[norm] = rel_path
    
    # 2. Match Items
    mapping = {}
    matched_count = 0
    
    print("Matching items...")
    for item in items_list:
        name = item['name']
        if name.startswith('#') or name == 'fuel': continue
        
        norm_name = normalize_name(name)
        
        if norm_name in image_files:
            mapping[name] = image_files[norm_name]
            matched_count += 1

    print(f"Mapped {matched_count} items to files.")

    if matched_count == 0:
        print("Error: No items matched!")
        return

    # 3. Save Map
    with open(OUTPUT_MAP_FILE, 'w') as f: json.dump(mapping, f, indent=2)
    
    # 4. Build App
    generate_standalone_html(items_list, mapping)

if __name__ == "__main__":
    main()