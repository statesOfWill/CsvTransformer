package com.csv.transform.models.database;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class DbCell {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String data;

    public DbCell(String data){
        this.data = data;
    }
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

//    private Integer excelIndexToIntId(String excelIndex) {
//        var excelIndexArr = excelIndex.split("");
//        StringBuilder strId = new StringBuilder();
//        for(int i = 0; i < excelIndexArr.length; i++){
//            var strIndex = excelIndexArr[i];
//            var intIndex = Integer.parseInt(strIndex);
//            strId.append(intIndex);
//        }
//        return Integer.parseInt(strId.toString());
//    }
}
