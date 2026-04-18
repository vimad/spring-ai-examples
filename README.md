``
brew install httpie
``

Get collections
```
http :6333/collections -b
```

In Qdrant terminology, document chunks are called points (meaning that each chunk resides at some point in multidimensional space). To get a count of points, you can submit a POST request to the API:
```
http POST :6333/collections/GameRules/points/count exact:=true -b
```