package com.csv.transform.mysqlDataAccess;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class DbCell {
    @Id
    private Integer id;
    private String data;

    public DbCell(String data, String excelIndex){
        this.data = data;
        this.id = excelIndexToIntId(excelIndex);
    }

    private Integer excelIndexToIntId(String excelIndex) {
        var excelIndexArr = excelIndex.split("");
        StringBuilder strId = new StringBuilder();
        for(int i = 0; i < excelIndexArr.length; i++){
            var strIndex = excelIndexArr[i];
            var intIndex = Integer.parseInt(strIndex);
            strId.append(intIndex);
        }
        return Integer.parseInt(strId.toString());
    }
}
