package edu.polo.ghostkitchen.classes;

import edu.polo.ghostkitchen.entidades.Detail;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
public class CartAdm {

    private List<Detail> detailList = new ArrayList<>();

    public List<Detail> getDetailList() {
        return detailList;
    }

    public void addDetail(Detail detail) {

        detailList.add(detail);
    }

    public void limpiar() {
        detailList.clear();
    }

    public void removeOne(Detail dishToRemove) {
        detailList.remove(dishToRemove);
    }

    public int getSizeList() {

        return detailList.size();
        
    }
}
