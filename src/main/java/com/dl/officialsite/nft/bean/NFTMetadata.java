package com.dl.officialsite.nft.bean;

import lombok.Data;

import java.util.List;

@Data
public class NFTMetadata {
    private String name;
    private String description;
    private String image;
    private List<Attribute> attributes;

}

@Data
class Attribute {
    private String trait_type;
    private String value;
}
