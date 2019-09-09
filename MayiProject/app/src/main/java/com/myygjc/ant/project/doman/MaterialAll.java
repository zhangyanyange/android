package com.myygjc.ant.project.doman;

import java.util.List;

/**
 * Created by zy2 on 2017/3/27.
 */

public class MaterialAll {

    /**
     * jh : [{"type":null,"unit":"个","spec":"25","price":12.2,"qty":34,"js":0,"zl":0,"tp":"/webMvc/Files/Upload/a1a00e1e7b5f4fb48bc95ece5119c7ca.jpg","mstp":null,"id":1527,"fullname":null,"name":"金牛25外丝直接（白）","deleted":false,"disabled":false,"number":null},{"type":null,"unit":"个","spec":"32","price":85,"qty":148,"js":0,"zl":0,"tp":null,"mstp":null,"id":1563,"fullname":null,"name":"水料_金牛_金牛32内丝单活接球阀（白）","deleted":false,"disabled":false,"number":null},{"type":null,"unit":"个","spec":"25","price":1.8,"qty":262,"js":0,"zl":0,"tp":"/webMvc/Files/Upload/9398fd0469204e82a2fc21920d5a6586.jpg","mstp":null,"id":1528,"fullname":null,"name":"金牛25管箍（白）","deleted":false,"disabled":false,"number":null},{"type":null,"unit":"个","spec":"25 90°","price":1.6,"qty":163,"js":0,"zl":0,"tp":null,"mstp":null,"id":1485,"fullname":null,"name":"水料_金牛_金牛25 90°弯头（白）","deleted":false,"disabled":false,"number":null},{"type":null,"unit":"个","spec":"32","price":90,"qty":9,"js":0,"zl":0,"tp":null,"mstp":null,"id":1569,"fullname":null,"name":"水料_金牛_金牛32双活接球阀（白）","deleted":false,"disabled":false,"number":null},{"type":null,"unit":"个","spec":"25*20（1/2）","price":9.4,"qty":41,"js":0,"zl":0,"tp":"","mstp":null,"id":1493,"fullname":null,"name":"金牛25*20（1/2）内丝弯头（白）","deleted":false,"disabled":false,"number":null},{"type":null,"unit":"个","spec":"25","price":2.4,"qty":131,"js":0,"zl":0,"tp":"/webMvc/Files/Upload/01796722e0774d78802b3ac0481df60f.jpg","mstp":null,"id":1517,"fullname":null,"name":"金牛25三通(白)","deleted":false,"disabled":false,"number":null},{"type":null,"unit":"个","spec":"20","price":47,"qty":134,"js":0,"zl":0,"tp":"/webMvc/Files/Upload/dc942a4066144656b0241b3b20af13b0.jpg","mstp":null,"id":1469,"fullname":null,"name":"金牛20双活接球阀(白)","deleted":false,"disabled":false,"number":null},{"type":null,"unit":"个","spec":"20","price":10.9,"qty":533,"js":0,"zl":0,"tp":"/webMvc/Files/Upload/6005449b28004bf89b52faa7c9a718b0.jpg","mstp":null,"id":1603,"fullname":null,"name":"金牛绿翡翠20内丝三通","deleted":false,"disabled":false,"number":null},{"type":null,"unit":"个","spec":"25 45°","price":1.6,"qty":489,"js":0,"zl":0,"tp":null,"mstp":null,"id":1484,"fullname":null,"name":"水料_金牛_金牛25 45°弯头(白)","deleted":false,"disabled":false,"number":null},{"type":null,"unit":"个","spec":"32","price":102.5,"qty":7,"js":0,"zl":0,"tp":null,"mstp":null,"id":1561,"fullname":null,"name":"水料_金牛_金牛32过滤器球阀","deleted":false,"disabled":false,"number":null},{"type":null,"unit":"个","spec":"32 90°","price":2.6,"qty":1097,"js":0,"zl":0,"tp":null,"mstp":null,"id":1544,"fullname":null,"name":"水料_金牛_金牛32 90°弯头（白）","deleted":false,"disabled":false,"number":null},{"type":null,"unit":"根","spec":"32","price":48,"qty":29,"js":0,"zl":0,"tp":null,"mstp":null,"id":1572,"fullname":null,"name":"水料_金牛_金牛32外丝活接（白）","deleted":false,"disabled":false,"number":null},{"type":null,"unit":"个","spec":"32","price":46,"qty":69,"js":0,"zl":0,"tp":null,"mstp":null,"id":1564,"fullname":null,"name":"水料_金牛_金牛32内丝活接（白）","deleted":false,"disabled":false,"number":null},{"type":null,"unit":"个","spec":"20","price":43,"qty":56,"js":0,"zl":0,"tp":"/webMvc/Files/Upload/e8a8bd8688184891979b393109b25995.jpg","mstp":null,"id":1463,"fullname":null,"name":"金牛20内丝单活接球阀（白）","deleted":false,"disabled":false,"number":null}]
     * pageCount : 6
     * totalCount : 85
     */

    private int pageCount;
    private int totalCount;
    private List<JhBean> jh;

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<JhBean> getJh() {
        return jh;
    }

    public void setJh(List<JhBean> jh) {
        this.jh = jh;
    }

    public static class JhBean {
        /**
         * type : null
         * unit : 个
         * spec : 25
         * price : 12.2
         * qty : 34.0
         * js : 0.0
         * zl : 0.0
         * tp : /webMvc/Files/Upload/a1a00e1e7b5f4fb48bc95ece5119c7ca.jpg
         * mstp : null
         * id : 1527
         * fullname : null
         * name : 金牛25外丝直接（白）
         * deleted : false
         * disabled : false
         * number : null
         */

        private Object type;
        private String unit;
        private String spec;
        private double price;
        private double qty;
        private double js;
        private double zl;
        private String tp;
        private String mstp;
        private int id;
        private Object fullname;
        private String name;
        private boolean deleted;
        private boolean disabled;
        private Object number;

        public Object getType() {
            return type;
        }

        public void setType(Object type) {
            this.type = type;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getSpec() {
            return spec;
        }

        public void setSpec(String spec) {
            this.spec = spec;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public double getQty() {
            return qty;
        }

        public void setQty(double qty) {
            this.qty = qty;
        }

        public double getJs() {
            return js;
        }

        public void setJs(double js) {
            this.js = js;
        }

        public double getZl() {
            return zl;
        }

        public void setZl(double zl) {
            this.zl = zl;
        }

        public String getTp() {
            return tp;
        }

        public void setTp(String tp) {
            this.tp = tp;
        }

        public String getMstp() {
            return mstp;
        }

        public void setMstp(String mstp) {
            this.mstp = mstp;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getFullname() {
            return fullname;
        }

        public void setFullname(Object fullname) {
            this.fullname = fullname;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isDeleted() {
            return deleted;
        }

        public void setDeleted(boolean deleted) {
            this.deleted = deleted;
        }

        public boolean isDisabled() {
            return disabled;
        }

        public void setDisabled(boolean disabled) {
            this.disabled = disabled;
        }

        public Object getNumber() {
            return number;
        }

        public void setNumber(Object number) {
            this.number = number;
        }
    }
}
