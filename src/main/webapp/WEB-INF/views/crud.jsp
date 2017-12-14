<%-- 
    Document   : crud
    Created on : 12 déc. 2017, 14:16:01
    Author     : ando
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--<link href="css/bootstrap3.min.css" rel="stylesheet">-->  
        <link href="css/bootstrap.min.css" rel="stylesheet">  
        <title>crud taxo</title>
    </head>
    <body>
        <h2 class="h2">AJOUT TAXONOMIE</h2>
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <form action="">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label>Higher classification:</label>
                                            <input type="text" name="higherClassification" class="form-control">
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label>Kingdom :</label>
                                            <input type="text" name="kingdom" class="form-control">
                                        </div>
                                    </div>
                                </div>

                                <!--                            <div class="form-horizontal">-->



                                <label>Phylum :</label>
                                <input type="text" name="phylum" class="form-control">
                                <label>Darwin class :</label>
                                <input type="text" name="dwcclass" class="form-control">
                                <label>Darwin order :</label>
                                <input type="text" name="dwcorder" class="form-control">
                                <label>Darwin family :</label>
                                <input type="text" name="dwcfamily" class="form-control">
                                <label>Genus :</label>
                                <input type="text" name="genus" class="form-control">
                                <label>Genus source :</label>
                                <input type="text" name="genussource" class="form-control">
                                <label>Specific epithet :</label>
                                <input type="text" name="specificepithet" class="form-control">
                                <label>Specific epithet source :</label>
                                <input type="text" name="specificepithetsource" class="form-control">
                                <label>Infra specific epithet :</label>
                                <input type="text" name="infraspecificepithet" class="form-control">
                                <label>Infra specific epithet source :</label>
                                <input type="text" name="infraspecificepithetsource" class="form-control">
                                <label>Scientific name :</label>
                                <input type="text" name="scientificname" class="form-control">
                                <label>Scientific name authorship :</label>
                                <input type="text" name="scientificnameauthorship" class="form-control">
                                <label>Accepted name usage :</label>
                                <input type="text" name="acceptednameusage" class="form-control">
                                <label>Basis of record :</label>
                                <input type="text" name="basisofrecord" class="form-control">
                                <label>French vernacular name :</label>
                                <input type="text" name="frenchvernacularname" class="form-control">
                                <!--                            </div>horizontal-->
                                <br>
                                <button type="submit" class="btn btn-primary">Ajouter</button>

                            </div><!--md6-->
                            <div class="col-md-6">
                                <!--                            <div class="form-horizontal">-->

                                <label>Malagasy vernacular name :</label>
                                <input type="text" name="malagasyvernacularname" class="form-control">
                                <label>English vernacular name :</label>
                                <input type="text" name="englishvernacularname" class="form-control">
                                <label>Habitat fr :</label>
                                <input type="text" name="habitat_fr" class="form-control">
                                <label>Habitat en :</label>
                                <input type="text" name="habitat_en" class="form-control">
                                <label>Habitat source :</label>
                                <input type="text" name="habitatsource" class="form-control">
                                <label>Ecology fr :</label>
                                <input type="text" name="ecology_fr" class="form-control">
                                <label>Ecology en :</label>
                                <input type="text" name="ecology_en" class="form-control">
                                <label>Ecology source :</label>
                                <input type="text" name="ecologysource" class="form-control">
                                <label>Behavior fr :</label>
                                <input type="text" name="behavior_fr" class="form-control">
                                <label>Behavior en :</label>
                                <input type="text" name="ecology_fr" class="form-control">
                                <label>Behavior source :</label>
                                <input type="text" name="behaviorsource" class="form-control">
                                <label>Threat fr :</label>
                                <input type="text" name="threat_fr" class="form-control">
                                <label>Threat en :</label>
                                <input type="text" name="threat_en" class="form-control">
                                <label>Threat source :</label>
                                <input type="text" name="threatsource" class="form-control">
                                <label>Morphology fr :</label>
                                <input type="text" name="morphology_fr" class="form-control">
                                <label>Morphology en :</label>
                                <input type="text" name="morphology_en" class="form-control">
                                <label>Protected area occurrences :</label>
                                <input type="text" name="morphology_fr" class="form-control">
                                <!--                            </div>horizontal-->

                            </div> <!--md6-->
                        </div> <!--row-->
                    </form><!--form-->
                </div>   <!--md12-->  
            </div> <!--container-->
        </div>

        <!--        <h1>MODIFICATION TAXONOMIE</h1>
                <h1>SUPPRESSION TAXONOMIE</h1>-->
    </body>
</html>
