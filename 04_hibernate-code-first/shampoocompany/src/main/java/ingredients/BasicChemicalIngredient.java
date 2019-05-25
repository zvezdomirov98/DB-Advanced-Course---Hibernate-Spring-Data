package ingredients;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.math.BigDecimal;

@MappedSuperclass
public class BasicChemicalIngredient extends BasicIngredient implements ChemicalIngredient {
    private String chemicalFormula;

    public BasicChemicalIngredient() {

    }

    public BasicChemicalIngredient(String name, BigDecimal price, String chemicalFormula) {
        super(name, price);
        setChemicalFormula(chemicalFormula);
    }

    @Column
    @Override
    public String getChemicalFormula() {
        return this.chemicalFormula;
    }

    public void setChemicalFormula(String chemicalFormula) {
        this.chemicalFormula = chemicalFormula;
    }
}
