<%@ page import="Projet.Model.Etudiant" %>
<%@ page import="java.util.List" %>
<%@ page import="Projet.Model.Groupe" %>
<%@ page import="Projet.Model.Module" %>
<%@ page import="java.util.HashSet" %><%--
  Created by IntelliJ IDEA.
  User: jolan
  Date: 19/01/2021
  Time: 14:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type='text/javascript' src="<%=application.getContextPath()%>/Public/javascript/admin.js"
        charset="UTF-8"></script>
<script type="text/javascript">
    const urlDeleteEtudiant = "<%=application.getContextPath()%>/etudiant/delete";
</script>

<%  List<Etudiant> etudiants = (List<Etudiant>) request.getAttribute("etudiants");
    List<Groupe> groupes = (List<Groupe>) request.getAttribute("groupes");%>
<div class="container">
    <div class="row justify-content-center">
        <table class="table table-striped table-dark">

            <h1>LISTE DES ETUDIANTS </h1>
            <thead>
            <tr>
                <th scope="col">Id</th>
                <th scope="col">Nom</th>
                <th scope="col">Prénom</th>
                <th scope="col">Groupe</th>
                <th scope="col">Actions</th>
            </tr>
            </thead>
            <tbody>
            <%
                for(Etudiant etudiant : etudiants) {%>
            <tr>
                <td><%=etudiant.getId()%></td>
                <td><%=etudiant.getNom()%></td>
                <td><%=etudiant.getPrenom()%></td>
                <td><%=etudiant.getGroupe().getNom()%></td>
                <td><button onClick="deleteEtudiant(<%=etudiant.getId()%>, this)">supprimer</button></td>
            </tr>
            <% } %>
            <tr>
                <th scope="row">Nouveau :</th>
                <form method="post" action="<%= application.getContextPath()%>/etudiant/create">
                    <td><input type="text" name="nom" placeholder="Nom de l'étudiant" required/></td>
                    <td><input type="text" name="prenom" placeholder="Prénom de l'étudiant" required/></td>
                    <td><select class="form-select"  name="etudiantGroupe">
                        <% for(Groupe groupe: groupes) {%>
                        <option value="<%=groupe.getId()%>"><%=groupe.getNom()%></option>
                        <% } %>
                    </select>
                    </td>
                    <td><input type="submit" value="Ajouter"/></td>
                </form>
            </tbody>
        </table>
    </div>
</div>