package app.itmart.shoppingappuser.UserModels;

public class ProductsModel {
    String prodname;
    String proddesc;
    String prodprice;
    String prodcat;
    String prodimageurl;

    public ProductsModel() {
    }

    public ProductsModel(String prodname, String proddesc, String prodprice, String prodcat, String prodimageurl) {
        this.prodname = prodname;
        this.proddesc = proddesc;
        this.prodprice = prodprice;
        this.prodcat = prodcat;
        this.prodimageurl = prodimageurl;
    }

    public String getProdname() {
        return prodname;
    }

    public void setProdname(String prodname) {
        this.prodname = prodname;
    }

    public String getProddesc() {
        return proddesc;
    }

    public void setProddesc(String proddesc) {
        this.proddesc = proddesc;
    }

    public String getProdprice() {
        return prodprice;
    }

    public void setProdprice(String prodprice) {
        this.prodprice = prodprice;
    }

    public String getProdcat() {
        return prodcat;
    }

    public void setProdcat(String prodcat) {
        this.prodcat = prodcat;
    }

    public String getProdimageurl() {
        return prodimageurl;
    }

    public void setProdimageurl(String prodimageurl) {
        this.prodimageurl = prodimageurl;
    }
}
