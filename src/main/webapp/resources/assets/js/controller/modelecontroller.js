/* ﻿
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
app.controller("modele", function ($scope, $http) {
    $scope.modeles = [];
    $scope.modele = {};
    $scope.recherche = "";
    $scope.file = {};
    $scope.pages = [];
    $scope.recherchePagination = 0;
    $scope.pageEnCours = 0;
    $scope.lastPage = 0;
    getall();

    function getall() {
        $http({
            method: 'GET',
            url: 'modeles',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function success(response) {
            $scope.modeles = response.data;
            $("#loader-spinner").hide();
            $http({
                method: 'GET',
                url: 'modeles/total',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }
            }).then(function success(response) {
                paginer(response.data, 10, 1);
            }, function error(response) {
                console.log(response);
            });
        }, function error(response) {
            console.log(response);
            $("#loader-spinner").hide();
        });
    }
    function paginer(liste, pas, position) {
        $scope.pages = [];
        var temp = liste / pas;
        temp = temp.toFixed(0);
        temp = parseInt(temp);
        if (temp * pas < liste)
            temp += 1;
        var index = 0;
        for (var i = 0; i < temp; i++) {
            if (position - 3 <= i && i < position + 3) {
                $scope.pages[index] = i + 1;
                index++;
            }
        }
        $scope.pageEnCours = position;
        $scope.lastPage = temp;
        $('#pageFin').val(temp);
        $('#pageEnCours').val(position);
    }

    $scope.editer = function (id) {
        $('#idModele').val(id);
    };
    $scope.ajouter = function () {
        $('#idModele').val(-1);
    };
    $scope.del = function (id) {
        $('#idToDelete').val(id);
    };

    $scope.upload = function () {
        var formData = new FormData();
        var file = $('#modele-asc')[0].files[0];
        formData.append("modele", file);
        formData.append("fileName", $('#newModeleName').val());
        var meth = 'POST';
        var url = 'modeles';
        if ($('#idModele').val() != -1) {
            url = 'modele/' + $('#idModele').val();
            meth = 'PUT';
        }
        console.log($('#idModele').val() != -1);
        $http({
            method: 'POST',
            url: url,
//            enctype: 'multipart/form-data',
            data: formData,
            headers: {
                'Accept': 'application/json',
                'Content-Type': undefined
            }
        }).then(function success(response) {
            getall();
            $('#modal-add-edit-modele').modal('hide');
            $('#idModele').val(-1);
            $('#modal-upload_spinner').modal('hide');
        }, function error(response) {
            $('#modal-alert').modal({backdrop: 'static'});
            $('#modal-add-edit-modele').modal('hide');
            $('#modal-upload_spinner').modal('hide');
        });
    };

    $scope.deleteModel = function () {
        if ($('#idToDelete').val() != -1) {
            $http({
                method: 'DELETE',
                url: 'modele/' + $('#idToDelete').val(),
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }
            }).then(function success(response) {
                $('#modal-delete-modele').modal('hide');
                getall();
            }, function error(response) {
                console.log(response.statusText);
            });
        }
    };

    $scope.rechercher = function (page) {
        if ($scope.recherchePagination === 1) {
            $http({
                method: 'GET',
                url: 'rechercherGlobaleEspeceDcwPage?champ=' + $('#rechercheGlobale').val() + '&page=' + page,
                data: angular.toJson($scope.modele),
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }
            }).then(function success(response) {
                $scope.liste = response.data;
                paginer($scope.liste[0].total, 20, page);
//            $('#pageEnCours').val(1);
            }, function error(response) {
                console.log(response);
            });
        } else {
            var formData = new FormData();
            var dwcs = $scope.modele.scientificname;
            if (dwcs == undefined)
                dwcs = null;

            var etat = $('[name="etat[]"]');
            var state = [];
            var etatS = "";
            var j = 0;
            for (var i = 0; i < etat.length; i++) {
                if (etat[i].checked == true) {
                    state[j] = etat[i].value;
                    j++;
                    etatS += etat[i].value;
                    if (i > 0) {
                        etatS += "&etat[]=" + etat[i].value;
                    }
                }
            }
            var validationMine = parseInt($('[name="validationMine"]').val());
            console.log(validationMine);
            if (isNaN(validationMine))
                validationMine = -999;

            console.log(dwcs);
            formData.append("dwcs", dwcs);
            formData.append("page", page);

            formData.append("validation", parseInt($('select[name=validation]').val()));
            formData.append("etat[]", state);
            formData.append("validationMine", validationMine);
            formData.append("espece", $('input[name=espece]').val());

            $http({
                method: 'POST',
                url: 'findByespeceDwcPaginatedSearch',
                data: formData,
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': undefined
                }
            }).then(function success(response) {
                $scope.liste = response.data;
                paginer($scope.liste[0].total, 20, page);
//            $('#pageEnCours').val(page);
//            $scope.recherche = $scope.modele.scientificname;
            }, function error(response) {
                console.log(response.statusText);
            });
        }
    };

    $scope.rechercherFin = function () {
        console.log($scope.recherchePagination);
        if ($scope.recherchePagination === 1) {
            $http({
                method: 'GET',
                url: 'rechercherGlobaleEspeceDcwPage?champ=' + $('#rechercheGlobale').val() + '&page=' + $('#pageFin').val(),
                data: angular.toJson($scope.modele),
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }
            }).then(function success(response) {
                $scope.liste = response.data;
                paginer($scope.liste[0].total, 20, $('#pageFin').val());
//            $('#pageEnCours').val(1);
            }, function error(response) {
                console.log(response);
            });
        } else {
            var formData = new FormData();
//        var dwcs = angular.toJson($scope.modele);                
//        var temp = dwcs.scientificName;
            var dwcs = $scope.modele.scientificName;
            if (dwcs == undefined)
                dwcs = null;
            console.log(dwcs);
            console.log($('#pageFin').val());

            var etat = $('[name="etat[]"]');
            var state = [];
            var etatS = "";
            var j = 0;
            for (var i = 0; i < etat.length; i++) {
                if (etat[i].checked == true) {
                    state[j] = etat[i].value;
                    j++;
                    etatS += etat[i].value;
                    if (i > 0) {
                        etatS += "&etat[]=" + etat[i].value;
                    }
                }
            }
            var validationMine = parseInt($('[name="validationMine"]').val());
            console.log(validationMine);
            if (isNaN(validationMine))
                validationMine = -999;


            formData.append("dwcs", dwcs);
            formData.append("page", $('#pageFin').val());

            formData.append("validation", parseInt($('select[name=validation]').val()));
            formData.append("etat[]", state);
            formData.append("validationMine", validationMine);
            formData.append("espece", $('input[name=espece]').val());

            $http({
                method: 'POST',
                url: 'findByespeceDwcPaginatedSearch',
                data: formData,
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': undefined
                }
            }).then(function success(response) {
                $scope.liste = response.data;
                paginer($scope.liste[0].total, 20, $('#pageFin').val());
//            $('#pageEnCours').val($('#pageFin').val());
                $scope.recherche = $scope.modele.scientificname;
            }, function error(response) {
                console.log(response.statusText);
            });
        }
    };

    $scope.rechercheGlobale = function () {
        $http({
            method: 'GET',
            url: 'rechercherGlobaleEspeceDcw?champ=' + $('#rechercheGlobale').val(),
            data: angular.toJson($scope.modele),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function success(response) {
            $scope.liste = response.data;
            paginer($scope.liste[0].total, 20, 1);
            $scope.recherchePagination = 1;
//            $('#pageEnCours').val(1);
        }, function error(response) {
            console.log(response);
        });
    };

});

