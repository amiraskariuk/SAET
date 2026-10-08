package com.amir.askari.saet.shared.data

object ProductFixtures {

    const val PRODUCTS_JSON = """
{
  "hits": [
    {
      "id": 1,
      "title": "Training Leggings",
      "colour": "Navy",
      "price": 50,
      "labels": ["going-fast"],
      "inStock": true,
      "featuredMedia": { "src": "https://cdn.example.com/1-front.jpg", "position": 1 },
      "media": [
        { "src": "https://cdn.example.com/1-front.jpg", "position": 1 },
        { "src": "https://cdn.example.com/1-back.jpg", "position": 2 }
      ],
      "availableSizes": [
        { "size": "xs", "inStock": true, "inventoryQuantity": 5 }
      ],
      "description": "<p>Soft and stretchy.</p>",
      "type": "Leggings",
      "fit": "mid-rise",
      "sku": "TL-NAVY",
      "objectID": "1",
      "unknownField": { "nested": "ignored" }
    },
    {
      "id": 2,
      "title": "Flex High Waisted Leggings",
      "colour": "Black",
      "price": 65,
      "labels": [],
      "inStock": true,
      "featuredMedia": null,
      "media": [
        { "src": "", "position": 1 },
        { "src": "https://cdn.example.com/2-side.jpg", "position": 2 }
      ]
    },
    {
      "id": 3,
      "title": "Adapt Seamless Leggings",
      "colour": "Rose Pink",
      "price": 1000,
      "labels": null,
      "inStock": false,
      "availableSizes": [
        { "size": "s", "inStock": true, "inventoryQuantity": -6 }
      ]
    },
    {
      "title": "Product Without Id",
      "price": 60
    }
  ]
}
"""
}
