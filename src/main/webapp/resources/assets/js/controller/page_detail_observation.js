/* ï»¿
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
app.controller("controller", function ($scope, $http, $sce) {
    $scope.object = {};
    $scope.liste = [];
    $scope.photos = [];
    $scope.videos = {};
    $scope.alerte = "";
    getallPhotos();
    getallVideos();
    var urlLoad = $('#load').val();
    var urlSet = $('#set').val();
    var urlFind = $('#find').val();
    var nomVariableLoad = $('#nomVariableLoad').val();
    $scope.urlSave = "";
    load();
    $scope.trustSrc = function (src) {
        return $sce.trustAsResourceUrl(src);
    }    

    function load() {
        var formData = {
            'id': $('#idDwc').val(),
            'idUtilisateurUpload': $('#idUtilisateur').val()
        };
        $http({
            method: 'POST',
            url: urlLoad,
            data: formData,
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function success(response) {
            $scope.liste = response.data;
        }, function error(response) {
            console.log(response);
        });
    }
//    $scope.save = function () {
//        $http({
//            method: 'POST',
//            url: urlSet,
//            data: angular.toJson($scope.object),
//            headers: {
//                'Accept': 'application/json',
//                'Content-Type': 'application/json'
//            }
//        }).then(function success(response) {
//            window.location = 'detailLemurien?id='+$('#idDwc').val();
//        }, function error(response) {
//            console.log(response.statusText);
//        });
//    };

    $scope.save = function () {
        $http({
            method: 'POST',
            url: urlSet,
            data: angular.toJson($scope.object),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function success(response) {
            load();
            $('#textAreaComment').val("");
        }, function error(response) {
            console.log(response.statusText);
        });
    };

    $scope.valider = function (id) {
        var formData = {
            'id': id
        };
        $http({
            method: 'POST',
            url: 'validerDwc',
            data: formData,
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function success(response) {
            window.location = 'detailLemurien?id=' + $('#idDwc').val();
        }, function error(response) {
            console.log(response.statusText);
        });
    };

    $scope.rechercher = function () {
        $http({
            method: 'POST',
            url: urlFind,
            data: angular.toJson($scope.object),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function success(response) {
            $scope.liste = response.data;
        }, function error(response) {
            console.log(response.statusText);
        });
    };

//    $scope.uploadPhoto = function () {
//        var formData = new FormData();
//        var file = $('#photo')[0].files[0];
//        var id = $('#idDwc').val();
//        var profil = $('#profil').is(":checked");
//        var valeurProfil = 0;
//        var datePrisePhoto = $('#datePrisePhoto').val();
//        console.log("1111");
//        console.log(datePrisePhoto);
//        if (profil)
//            valeurProfil = 1;
//        formData.append("photo", file);
//        formData.append("profil", valeurProfil);
//        formData.append("idDarwinCore", id);
//        formData.append("datePrisePhoto", datePrisePhoto);                
//        $http({
//            method: 'POST',
//            url: 'uploadImageDarwinCore',
////            enctype: 'multipart/form-data',
//            data: formData,
//            headers: {
//                'Accept': 'application/json',
//                'Content-Type': undefined
//            }
//        }).then(function success(response) {
//            $scope.photos = response.data;
//            alert("la photo a été téléchargée avec succes");
//            $('#uploadPhoto')[0].reset();
//        }, function error(response) {
//            console.log(response.statusText);
//            alert("un probleme est survenu lors du téléchargement de la photo");
//        });
//    };

    $scope.uploadPhoto = function () {
        var formData = new FormData();
        var file = $('#photo')[0].files[0];
        var id = $('#idDwc').val();
        var profil = $('#profil').is(":checked");
        var valeurProfil = 0;
        var datePrisePhoto = $('#datePrisePhoto').val();
        if (profil)
            valeurProfil = 1;
        formData.append("photo", file);
        formData.append("profil", valeurProfil);
        formData.append("idDarwinCore", id);
        formData.append("datePrisePhoto", datePrisePhoto);
        console.log(valeurProfil);
        console.log(formData);
        $http({
            method: 'POST',
            url: 'uploadImageDarwinCore',
//            enctype: 'multipart/form-data',
            data: formData,
            headers: {
                'Accept': 'application/json',
                'Content-Type': undefined
            }
        }).then(function success(response) {
            $scope.photos = response.data;
            $scope.alerte = "le fichier a été téléchargé avec succes";
            $('#modal-alert').modal({backdrop: 'static'});
            $('#uploadPhoto')[0].reset();
            $('#modal-upload-photo').hide();
        }, function error(response) {
            console.log(response.statusText);
            $scope.alerte = "Une erreur est survenu lors du téléchargement du fichier.";
            $('#modal-upload-photo').hide();
            $('#modal-alert').modal({backdrop: 'static'});
        });
    };

    $scope.uploadVideo = function () {
        var formData = new FormData();
        var id = $('#idDwc').val();
        var lien = $('#lien').val();
        formData.append("lien", lien);
        formData.append("idDarwinCore", id);
        console.log(lien);
        console.log(formData);
        $http({
            method: 'POST',
            url: 'uploadVideoDarwinCore',
//            enctype: 'multipart/form-data',
            data: formData,
            headers: {
                'Accept': 'application/json',
                'Content-Type': undefined
            }
        }).then(function success(response) {
            $scope.photos = response.data;
            $scope.alerte = "le fichier a été téléchargé avec succes";
            $('#modal-alert').modal({backdrop: 'static'});
            $('#uploadPhoto')[0].reset();
        }, function error(response) {
            console.log(response.statusText);
            $scope.alerte = "Une erreur est survenu lors du téléchargement du fichier.";
            $('#modal-alert').modal({backdrop: 'static'});
        });
    };

    function getallPhotos() {
        var data = "?idDarwinCore=" + $('#idDwc').val();
        $http({
            method: 'GET',
            url: 'getListPhotoDarwinCore' + data,
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function success(response) {
            console.log(response);
            $scope.photos = response.data;
        }, function error(response) {
            console.log(response.statusText);
        });
    }
    ;

    function getallVideos() {
        var data = "?idDarwinCore=" + $('#idDwc').val();
        $http({
            method: 'GET',
            url: 'getListVideoDarwinCore' + data,
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function success(response) {
            console.log(response.data[0].lien);
//            $scope.movie = {src: "http://www.youtube.com/embed/Lx7ycjC8qjE", title: "Egghead.io AngularJS Binding"};
            $scope.videos = {src : response.data[0].lien, title : "lemuriens"};
            console.log($scope.videos);
        }, function error(response) {
            console.log(response.statusText);
        });
    }
    ;
//
//    $scope.rechercherMulti=function(){
//        var formData = {
//                'validation': parseInt($('select[name=validation]').val()),
//                'chercheur': $('input[name=chercheur]').val()
//            };
//        $http({
//            method : 'POST',
//            url : 'findByespeceDwcs',
//            data : formData,
//            headers: {
//                'Accept': 'application/json',
//                'Content-Type': 'application/json'
//            }
////            dataType: 'json'
//        }).then(function success(response){
//            $scope.liste=response.data;
////            $scope.recherche=$scope.darwin.scientificname;
//        },function error(response){
//            console.log(response.statusText);
//        });
//    };
//
//
////    $(document).ready(function () {
////        $('#form-search').submit(function (event) {
////            // get the form data
////            var formData = {
////                'validation': $('select[name=validation]').val(),
////                'chercheur': $('input[name=chercheur]').val()
////
////            };
////            // process the form
////            $.ajax({
////                type: 'POST',
////                url: 'findByespeceDwcs',
////                data: formData,
////                dataType: 'json',
////                success: function (data) {
////                    $scope.liste = data.data;
////                }
////            });
////            // stop the form from submitting and refreshing
////            event.preventDefault();
////        });
////    });
//
//
//    $scope.editer = function (darwin) {
//        $("#editOrnew").modal({backdrop: 'static'});
//        $scope.form = angular.copy(darwin);
//    };
//
//    $scope.annuler = function () {
//        $("#editOrnew").modal("hide");
//        $scope.form = {};
//    };



//    $scope.delete = function (darwin) {
//        $http({
//            method: 'POST',
//            url: 'deleteDwc',
//            data: angular.toJson(darwin),
//            headers: {
//                'Accept': 'application/json',
//                'Content-Type': 'application/json'
//            }
//        }).then(function success(response) {
//            getall();
//        }, function error(response) {
//            console.log(response.statusText);
//        });
//    };
});

