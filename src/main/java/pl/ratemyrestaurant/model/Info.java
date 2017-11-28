package pl.ratemyrestaurant.model;

import pl.ratemyrestaurant.type.APIInfoCodes;

import java.util.UUID;

public class Info {

    private String key;
    private long httpStatusCode;
    private APIInfoCodes infoCode;

    public APIInfoCodes getInfoCode() {
        return infoCode;
    }

    public void setInfoCode(APIInfoCodes infoCode) {
        this.infoCode = infoCode;
    }

    private String desc;
    private Object object;

    public Info(){
        key = UUID.randomUUID().toString();
        httpStatusCode = 200L;
        infoCode = APIInfoCodes.OK;
    }

    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public long getHttpStatusCode() {
        return httpStatusCode;
    }
    public void setHttpStatusCode(long httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
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
        return "Info [httpStatusCode=" + httpStatusCode + ", desc=" + desc + ", object=" + object + "]";
    }

    public static class InfoBuilder {
        private Info info;

        public InfoBuilder() {
            info = new Info();
        }
        public InfoBuilder setHttpStatusCode(long code){
            info.setHttpStatusCode(code);
            return this;
        }
        public InfoBuilder setDescription(String desc) {
            info.setDesc(desc);
            return this;
        }
        public InfoBuilder setInfoCode(APIInfoCodes infoCode){
            info.setInfoCode(infoCode);
            return this;
        }
        public InfoBuilder setObject(Object object) {
            info.setObject(object);
            return this;
        }
        public Info build(){
            return info;
        }
    }
}