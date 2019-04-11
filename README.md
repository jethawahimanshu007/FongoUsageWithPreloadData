Usage of in memory MongoDB- Fongo, and preloading the data in a Spring Boot application

To use this as reference, add following things to your project:
1) DataSet.java - Modify all your collection objects which are defined as List
2) DataSetExtractor
3) CustomJackson2ResourceReader.java
4) CustomRepositoryPopulatorFactoryBean.java
5) FakeMongoMainConfig.java
6) Change the name of books.json in src/main/resources to any name you want, change the data as per your collections, and change in FakeMongoMainConfig file the bean Jackson2RepositoryPopulatorFactoryBean to populate data using your json file.


