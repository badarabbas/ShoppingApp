package app.itmart.shoppingapp.AdminActivities.AdminModels;

public class CategoriesModel {
    public CategoriesModel(String catname) {
        this.catname = catname;
    }

    public CategoriesModel() {
    }

    public String getCatname() {
        return catname;
    }

    public void setCatname(String catname) {
        this.catname = catname;
    }

    String catname;

    public CategoriesModel(String catname, String catimageurl) {
        this.catname = catname;
        this.catimageurl = catimageurl;
    }

    public String getCatimageurl() {
        return catimageurl;
    }

    public void setCatimageurl(String catimageurl) {
        this.catimageurl = catimageurl;
    }

    String catimageurl;
}
