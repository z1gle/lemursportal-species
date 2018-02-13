/* ï»¿
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
app.controller("taxonomi", function ($scope, $http) {
    $scope.liste = [];
    $scope.photos = [];
    $scope.recherche = "";
    $scope.taxonomi = {};
    $scope.total;
    $scope.form = {};
    getall();
    getallPhotos();

    function getall() {
        $http({
            method: 'POST',
            url: 'findByespeceTaxo',
            data: angular.toJson($scope.taxonomi),
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(function success(response) {
            $scope.liste = response.data;
        }, function error(response) {
            console.log(response.statusText);
        });
    }

    $scope.rechercher = function () {
        $http({
            method: 'POST',
            url: 'findByespeceTaxo',
            data: angular.toJson($scope.taxonomi),
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(function success(response) {
            $scope.liste = response.data;
            $scope.recherche = $scope.taxonomi.test;
        }, function error(response) {
            console.log(response.statusText);
        });
    };

    $scope.editer = function (darwin) {
        $("#editOrnew").modal({backdrop: 'static'});
        console.log(darwin);
        $scope.form = angular.copy(darwin);
    };

    $scope.detailTaxo = function (darwin) {
        window.location = "getDetailTaxo?id=" + darwin;
    };

    $scope.annuler = function () {
        $("#editOrnew").modal("hide");
        $scope.form = {};
    };

    $scope.save = function () {
        $http({
            method: 'POST',
            url: 'saveTaxo',
            data: angular.toJson($scope.form),
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(function success(response) {
            $scope.form = {};
            $("#editOrnew").modal("hide");
            getall();
        }, function error(response) {
            console.log(response.statusText);
        });
    };

    $scope.delete = function (taxonomi) {
        $http({
            method: 'POST',
            url: 'deleteTaxo',
            data: angular.toJson(taxonomi),
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(function success(response) {
            getall();
        }, function error(response) {
            console.log(response.statusText);
        });
    };

    $scope.uploadPhoto = function () {
        var formData = new FormData();
        var file = $('#photo')[0].files[0];
        var id = $('#idDwc').val();
        var profil = $('#profil').is(":checked");
        var valeurProfil = 0;
        if (profil)
            valeurProfil = 1;
        formData.append("photo", file);
        formData.append("profil", valeurProfil);
        formData.append("idTaxonomi", id);
        console.log(valeurProfil);
        console.log(formData);
        $http({
            method: 'POST',
            url: 'uploadImageTaxonomi',
//            enctype: 'multipart/form-data',
            data: formData,
            headers: {
                'Accept': 'application/json',
                'Content-Type': undefined
            }
        }).then(function success(response) {
            $scope.photos = response.data;
            alert("la photo a été téléchargée avec succes");
            $('#uploadPhoto')[0].reset();
        }, function error(response) {
            console.log(response.statusText);
            alert("un probleme est survenu lors du téléchargement de la photo");
        });
    };
    
    function getallPhotos() {        
        var data = "?idTaxonomi="+$('#idDwc').val();
        $http({
            method: 'GET',
            url: 'getListPhotoTaxonomi' + data,            
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
    };
    
    $scope.uploadByLink = function() {        
        $('#modal-upload_spinner').modal({backdrop: 'static'});;
        $http({
            method: 'GET',
            url: 'uploadByLink?url='+$('#link').val(),            
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function success(response) {
            console.log(response);            
            getall();
//            $scope.showAppLoader = false;
            $('#modal-upload_spinner').hide();
//            $scope.photos = response.data;            
        }, function error(response) {
            console.log(response.statusText);     
//            $scope.showAppLoader = false;
            $('#modal-upload_spinner').hide();
            alert("un erreur est survenu lors de l'upload");
        });    
    };

});