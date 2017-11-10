package pl.ratemyrestaurant.model;

import java.util.UUID;

public class Info {

    private String key;
    private Long code;
    private String desc;
    private Object object;

    public Info(){
        key = UUID.randomUUID().toString();
        code = 0L;
    }

    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public Long getCode() {
        return code;
    }
    public void setCode(Long code) {
        this.code = code;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return "Info [code=" + code + ", desc=" + desc + ", object=" + object + "]";
    }

}