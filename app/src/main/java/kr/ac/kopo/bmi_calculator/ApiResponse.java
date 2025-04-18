package kr.ac.kopo.bmi_calculator;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponse {
    @SerializedName("response")
    private ResponseData response;

    public ResponseData getResponse() {
        return response;
    }

    public class ResponseData {
        @SerializedName("body")
        private Body body;

        public Body getBody() {
            return body;
        }
    }

    public class Body {
        @SerializedName("items")
        private List<FoodItem> items;

        public List<FoodItem> getItems() {
            return items;
        }
    }

    public class FoodItem {
        @SerializedName("enerc")
        private String energy;

        @SerializedName("prot")
        private String protein;

        @SerializedName("sugar")
        private String sugar;

        public String getEnergy() { return energy; }
        public String getProtein() { return protein; }
        public String getSugar() { return sugar; }
    }
}
