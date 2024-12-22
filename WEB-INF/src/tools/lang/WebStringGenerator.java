package tools.lang;

import java.util.*;
import java.lang.reflect.*;

public class WebStringGenerator {

    public String chooseCorrectClass(Number ValeurMin, Number valeur){
        String answer = "none";
        if (valeur.doubleValue() > ValeurMin.doubleValue()) {
            answer = "table-success";
        } else if(valeur.doubleValue() < ValeurMin.doubleValue()) {
            answer = "table-danger";
        }
        return answer;
    }

    public String generateHtmlTable(List<Object> objects, Map<String, Number> somme, Map<String, Number> moyenne) {
        if (objects == null || objects.isEmpty()) {
            return "<p>Aucune donnée disponible.</p>";
        }
    
        StringBuilder html = new StringBuilder();
    
        html.append("<h1>");
        html.append("Contenu de la table " + objects.get(0).getClass().getSimpleName());
        html.append("</h1>");
        html.append("<table border='1' class='table table-hover'>");
        
        try {
            Object firstObject = objects.get(0);
            Class<?> clazz = firstObject.getClass();
            Field[] fields = clazz.getDeclaredFields();
    
            html.append("<thead><tr>");
            for (Field field : fields) {
                html.append("<th class='table-primary'>").append(field.getName()).append("</th>");
            }
            html.append("</tr><tr><h2>");
                html.append("Nombre d'éléments : ").append(objects.size());
            html.append("</h2></tr></thead>");
            
            html.append("<tbody>");
            for (Object obj : objects) {
                html.append("<tr>");
                for (Field field : fields) {
                    field.setAccessible(true);
                    Object value = field.get(obj);
                    Number colSum = moyenne.get(field.getName().toUpperCase());
                    String cssClass = "none";
                    if (colSum != null) {
                        if (value instanceof Number) {
                            cssClass = chooseCorrectClass(colSum, (Number) value);
                        } 
                    }
                    

                    html.append("<td class='").append(cssClass).append("'>").append(value != null ? value : "").append("</td>");
                }
                html.append("</tr>");
            }
            html.append("</tbody>");
            
            html.append("<tfoot><tr>");

            for (Field field : fields) {
                String fieldName = field.getName();
                
                Number colSum = somme.get(fieldName.toUpperCase());
                
                html.append("<td>");
                if (colSum != null) {
                    html.append("Somme: ").append(colSum);
                } else {
                    html.append("Somme: NAN");
                }
                html.append("</td>");
            }
            html.append("</tr>");
            html.append("<tr>");

            for (Field field : fields) {
                String fieldName = field.getName();
                //System.out.println(fieldName);
                                
                Number colAvg = moyenne.get(fieldName.toUpperCase());
                
                html.append("<td>");
                if (colAvg != null) {
                    html.append("Moyenne: ").append(colAvg);
                } else {
                    html.append("Moyenne: NAN");
                }
                html.append("</td>");
            }
            
            html.append("</tr></tfoot>");
            
        } catch (Exception e) {
            e.printStackTrace();
            return "<p>Erreur lors de la génération du tableau HTML.</p>";
        }
    
        html.append("</table>");
        return html.toString();
    }

    public String generateForm(Object obj) {
        StringBuilder formHtml = new StringBuilder();
        Class<?> clazz = obj.getClass();

        formHtml.append("<form class='container mt-4'>");

        for (Field field : clazz.getDeclaredFields()) {
            String fieldName = field.getName();
            String fieldType = field.getType().getSimpleName();

            formHtml.append("<div class='mb-3'>");

            formHtml.append("<label for='").append(fieldName).append("' class='form-label'>")
                    .append(fieldName).append("</label>");

            if (fieldType.equals("String")) {
                formHtml.append("<input type='text' class='form-control' id='")
                        .append(fieldName).append("' name='").append(fieldName).append("' />");
            } else if (fieldType.equals("int") || fieldType.equals("Integer") || fieldType.equals("double") || fieldType.equals("Double")) {
                formHtml.append("<input type='number' class='form-control' id='")
                        .append(fieldName).append("' name='").append(fieldName).append("' />");
            } else if (fieldType.equals("boolean") || fieldType.equals("Boolean")) {
                formHtml.append("<div class='form-check'>");
                formHtml.append("<input type='checkbox' class='form-check-input' id='")
                        .append(fieldName).append("' name='").append(fieldName).append("' />");
                formHtml.append("<label class='form-check-label' for='").append(fieldName)
                        .append("'>").append(fieldName).append("</label>");
                formHtml.append("</div>");
            } else {
                formHtml.append("<input type='text' class='form-control' id='")
                        .append(fieldName).append("' name='").append(fieldName).append("' />");
            }

            formHtml.append("</div>");
        }

        formHtml.append("<button type='submit' class='btn btn-primary'>Submit</button>");
        formHtml.append("</form>");

        return formHtml.toString();
    }

    public static String generateHtmlSelect(String id, String name, List<?> objects, String valueKey, String labelKey) {
        StringBuilder select = new StringBuilder();
        
        // Ouvre la balise <select>
        select.append("<select id=\"")
              .append(id)
              .append("\" name=\"")
              .append(name)
              .append("\">\n");
        
        // Ajoute les options
        for (Object obj : objects) {
            try {
                // Utilisation de la réflexion pour récupérer les valeurs dynamiquement
                Object value = obj.getClass().getMethod("get" + capitalize(valueKey)).invoke(obj);
                Object label = obj.getClass().getMethod("get" + capitalize(labelKey)).invoke(obj);

                // Ajout de l'option HTML
                select.append("  <option value=\"")
                      .append(value)
                      .append("\">")
                      .append(label)
                      .append("</option>\n");

            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Erreur lors de la génération des options HTML : " + e.getMessage());
            }
        }
        
        // Ferme la balise <select>
        select.append("</select>");
        
        return select.toString();
    }

    // Méthode utilitaire pour capitaliser le premier caractère d'une chaîne
    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    
}
