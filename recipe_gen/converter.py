import json
import os


def get_items(item_type, material):
    global tags

    item_list = []
    if item_type == "item":
        item_list.append(material)
    elif item_type == "tag":
        tag = material.replace("minecraft:", "")
        item_list += tags[tag]
    return item_list


def load_tag_file(f):
    global tags

    raw_data = open(f, "r")
    data = json.load(raw_data)
    name = f.replace(".json", "")
    name = name.replace("tags/items/", "")
    values = []
    for tag in data["values"]:
        if tag.startswith("#"):
            tag_name = tag.replace("#minecraft:", "")
            try:
                nested_tag = tags[tag_name]
                values += nested_tag
            except KeyError:
                return False
        else:
            values.append(tag)
    tags[name] = values
    return True


# load tags
block_files = os.listdir("tags/blocks")
item_files = os.listdir("tags/items")
tags = {}
while len(item_files) > 0:
    for item_file in item_files:
        success = load_tag_file("tags/items/" + item_file)
        if success:
            item_files.remove(item_file)
            break


files = os.listdir("recipes")
for f in files:
    raw_data = open("recipes/" + f, "r")
    data = json.load(raw_data)
    if data["type"] == "minecraft:crafting_shaped":
        result = data["result"]["item"]
        count = 1
        try:
            count = int(data["result"]["count"])
        except KeyError:
            pass
        pattern = data["pattern"]
        char_keys = []
        for line in pattern:
            for char in line:
                if not char in char_keys:
                    char_keys.append(char)
        keys = {" " : ["minecraft:air"]}
        for ck in char_keys:
            if ck != " ":
                items = []
                for entry in data["key"][ck]:
                    if isinstance(entry, dict):
                        for nested_entry in entry:
                            items += get_items(nested_entry, entry[nested_entry])
                    else:
                        items = get_items(entry, data["key"][ck][entry])
                keys[ck] = items

        new_json = {}
        new_json["result"] = result
        new_json["count"] = count
        new_json["pattern"] = pattern
        new_json["keys"] = keys
        raw_new_data = json.dumps(new_json, indent=4)
        f = open("shaped/" + f, "w")
        f.write(raw_new_data)
        f.close()

    if data["type"] == "minecraft:crafting_shapeless":
        result = data["result"]["item"]
        count = 1
        try:
            count = int(data["result"]["count"])
        except KeyError:
            pass
        ingredients = []
        for ingredient in data["ingredients"]:
            for entry in ingredient:
                items = get_items(entry, ingredient[entry])
                ingredients.append(items)

        new_json = {}
        new_json["result"] = result
        new_json["count"] = count
        new_json["ingredients"] = ingredients
        raw_new_data = json.dumps(new_json, indent=4)
        f = open("shapeless/" + f, "w")
        f.write(raw_new_data)
        f.close()
