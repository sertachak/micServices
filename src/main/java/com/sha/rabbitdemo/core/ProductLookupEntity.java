package com.sha.rabbitdemo.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "productLookup")
public class ProductLookupEntity {
    // normally it should be stored in a separate database
    @Id
    private String id;
    private String name;

}
