<%@ page import="connect.GenericDataBaseConnection" %>
<%@ page import="connect.dao.DAOExecuter" %>
<%@ page import="main.object.personne.Etudiant" %>
<%@ page import="main.object.etude.Matiere" %>
<%@ page import="main.object.evaluation.Note" %>
<%@ page import="tools.lang.WebStringGenerator" %>
<%@ page import="java.util.*" %>
<%@ page import="connect.dao.object.finance.Mode_paiementDAO" %>
<%@ page import="main.object.finance.Mode_Paiement" %>

<%

        GenericDataBaseConnection gdbc = new GenericDataBaseConnection();
        WebStringGenerator webTool = new WebStringGenerator();

        String urlMySQL = "jdbc:mysql://localhost:3306/school_admin";
        String userMySQL = "root";
        String passwordMySQL = "";

        String urlORCL = "jdbc:oracle:thin:@localhost:1521:orcl";
        String userORCL = "school_admin";
        String passwordORCL = "school";

        Note n = new Note();
        Etudiant e = new Etudiant();
        Matiere m = new Matiere();
        List<Object> o = null;

        Map<String, Object> lo = new HashMap<>();
        lo.put("admis", false);
        Map<String, Number> moyenne = null;
        Map<String, Number> somme = null;
        List<Mode_Paiement> eco = new ArrayList<>();
        try {
            gdbc.openConnection( "mysql", urlMySQL, userMySQL, passwordMySQL);
            gdbc.openConnection("oracle", urlORCL, userORCL, passwordORCL);

            Mode_paiementDAO ecolageDAO = new Mode_paiementDAO(gdbc.getAllConnection());
            eco = ecolageDAO.getAllMode_Paiement();
            if (eco == null) {
                System.out.println("valeur nulle");
            }
            

            
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            gdbc.closeAllConnection();
        }

        String tab = e.generateEtudiantForm(eco);
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="assets/css/bootstrap-grid.css">
    <link rel="stylesheet" href="assets/css/bootstrap-grid.rtl.css">
    <link rel="stylesheet" href="assets/css/bootstrap-utilities.rtl.css">
    <link rel="stylesheet" href="assets/css/bootstrap.css">
    <link rel="stylesheet" href="assets/css/bootstrap.rtl.css">
    <link rel="stylesheet" href="assets/css/MyCSS/index.css">
    <link rel="stylesheet" href="assets/css/MyCSS/button.css">
    <link rel="stylesheet" href="assets/css/MyCSS/font.css">
</head>
<body>
    <div class="container text-center">
        <div class="row">
            <div class="col">
                <%= tab %>
            </div>
        </div>
    </div>
</body>
</html>