/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duyhp.dtos;

/**
 *
 * @author Huynh Duy
 */
public class ProductErrorDTO {

    private String productNameError;
    private String descriptionError;
    private String imageNameError;
    private String priceErro;
    private String quantityError;

    public ProductErrorDTO() {
    }

    public ProductErrorDTO(String productNameError, String descriptionError, String imageNameError, String priceErro, String quantityError) {
        this.productNameError = productNameError;
        this.descriptionError = descriptionError;
        this.imageNameError = imageNameError;
        this.priceErro = priceErro;
        this.quantityError = quantityError;
    }

    public String getProductNameError() {
        return productNameError;
    }

    public void setProductNameError(String productNameError) {
        this.productNameError = productNameError;
    }

    public String getDescriptionError() {
        return descriptionError;
    }

    public void setDescriptionError(String descriptionError) {
        this.descriptionError = descriptionError;
    }

    public String getImageNameError() {
        return imageNameError;
    }

    public void setImageNameError(String imageNameError) {
        this.imageNameError = imageNameError;
    }

    public String getPriceErro() {
        return priceErro;
    }

    public void setPriceErro(String priceErro) {
        this.priceErro = priceErro;
    }

    public String getQuantityError() {
        return quantityError;
    }

    public void setQuantityError(String quantityError) {
        this.quantityError = quantityError;
    }
}
