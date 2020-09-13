import os, os.path as path
import json
from glob import glob
os.chdir(path.dirname(path.abspath(__file__)))

types = (
    ('aerogel', 'aerogel'),
    ('carved', 'carved_stone'),
    ('holystone', 'holystone'),
    ('mossy_holystone', 'mossy_holystone'),
    ('skyroot', 'skyroot_planks'),
    ('holystone_brick', 'holystone_bricks'),
    ('angelic', 'angelic_stone'),
    ('hellfire', 'hellfire_stone'),
)

for block, regular_name in types:
    with open(path.join("blockstates", f"{block}_wall.json"), 'w') as file:
        data = {
            "multipart": [
                {   "when": { "up": "true" },
                    "apply": { "model": f"aether:block/{block}_wall_post" }
                },
                {   "when": { "north": "true" },
                    "apply": { "model": f"aether:block/{block}_wall_side", "uvlock": True }
                },
                {   "when": { "east": "true" },
                    "apply": { "model": f"aether:block/{block}_wall_side", "y": 90, "uvlock": True }
                },
                {   "when": { "south": "true" },
                    "apply": { "model": f"aether:block/{block}_wall_side", "y": 180, "uvlock": True }
                },
                {   "when": { "west": "true" },
                    "apply": { "model": f"aether:block/{block}_wall_side", "y": 270, "uvlock": True }
                }
            ]
        }
        json.dump(data, file, indent=2, check_circular=False, sort_keys=False)
        print(file.name)
    with open(path.join("models", "block", f"{block}_wall_inventory.json"), 'w') as file:
        data = {
            "parent": "block/wall_inventory",
            "textures": {
                "wall": f"aether:block/{regular_name}"
            }
        }
        json.dump(data, file, indent=2, check_circular=False, sort_keys=False)
        print(file.name)
    with open(path.join("models", "block", f"{block}_wall_post.json"), 'w') as file:
        data = {
            "parent": "block/template_wall_post",
            "textures": {
                "wall": f"aether:block/{regular_name}"
            }
        }
        json.dump(data, file, indent=2, check_circular=False, sort_keys=False)
        print(file.name)
    with open(path.join("models", "block", f"{block}_wall_side.json"), 'w') as file:
        data = {
            "parent": "block/template_wall_side",
            "textures": {
                "wall": f"aether:block/{regular_name}"
            }
        }
        json.dump(data, file, indent=2, check_circular=False, sort_keys=False)
        print(file.name)
    with open(path.join("models", "item", f"{block}_wall.json"), 'w') as file:
        data = {
            'parent': f'aether:block/{block}_wall_inventory'
        }
        json.dump(data, file, indent=2, check_circular=False, sort_keys=False)
        print(file.name)