package zhentingmai.androidfinalproject;

/**
 * Created by gdyjm on 2017-12-17.
 */

public class AutoInfo {
        private String id;
        private String year;
        private String month;
        private String day;

        private String price;
        private String liters;
        private String kilo;

        public AutoInfo(String id, String year, String month, String day, String price, String liters, String kilo) {
            this.id = id;
            this.year = year;
            this.month = month;
            this.day = day;
            this.price = price;
            this.liters = liters;
            this.kilo = kilo;
        }

        public String getId() {
            return id;
        }

        public String getYear() {
            return year;
        }
        public String getMonth() {
            return month;
        }
        public String getDay() {
            return day;
        }

        public String getPrice() {
            return price;
        }

        public String getLiters() {
            return liters;
        }

        public String getKilo() {
            return kilo;
        }
}
