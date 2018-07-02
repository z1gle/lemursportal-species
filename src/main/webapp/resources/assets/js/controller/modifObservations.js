/* ﻿
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
app.controller("darwin", function ($scope, $http) {
    $scope.liste = [];
    $scope.page = 1;
    $scope.dwc = new Object();
    $scope.total = 0;
    $scope.page = 0;
    $scope.lastPage = 0;
    getall();

    function getall() {
        var dwc = new Object();
        dwc.id = 6;
        dwc.dwcyear = 1999;
        var page = 2;
        $http({
            method: 'POST',
            url: 'http://localhost:8080/lemurs/observations?page=' + page,
            data: dwc,
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function success(response) {
            $("#loader-spinner").hide();
            $scope.liste = response.data.retour.liste;
            $scope.total = response.data.retour.total;
            $scope.page = response.data.retour.page;
            $scope.lastPage = response.data.retour.lastPage;
            paginer(response.data.retour);
        }, function error(response) {
            console.log(response);
            $("#loader-spinner").hide();
        });
    }
    function paginer(liste) {
        console.log(liste);
        var previous = 1;
        if (liste.page > 2) previous = liste.page - 1;
        var items = [];   
        items.push('<li class=""><a href="#" ng-click="rechercher('+1+')"><i class="fa fa-angle-double-left"></i></a></li>');
        items.push('<li class=""><a href="#" ng-click="rechercher(' + previous + ')"><i class="fa fa-angle-left"></i></a></li>');
        if (liste.page < 6) {
            for (var i = 1; i < 6; i++) {
                if (i === liste.page) {
                    items.push('<li class="active" id=""><a href="#" ng-click="rechercher(' + i + ')">' + i + '</a></li>');
                } else {
                    items.push('<li class="" id=""><a href="#" ng-click="rechercher(' + i + ')">' + i + '</a></li>');
                }
            }
        } else if (liste.page > liste.lastPage - 5) {
            for (var i = liste.lastPage - 4; i <= liste.lastPage; i++) {
                if (i === liste.page) {
                    items.push('<li class="active" id=""><a href="#" ng-click="rechercher(' + i + ')">' + i + '</a></li>');
                } else {
                    items.push('<li class="" id=""><a href="#" ng-click="rechercher(' + i + ')">' + i + '</a></li>');
                }
            }
        } else {
            for (var i = liste.page - 2; i <= liste.page + 2; i++) {
                if (i === liste.page) {
                    items.push('<li class="active" id=""><a href="#" ng-click="rechercher(' + i + ')">' + i + '</a></li>');
                } else {
                    items.push('<li class="" id=""><a href="#" ng-click="rechercher(' + i + ')">' + i + '</a></li>');
                }
            }
        }
        items.push('<li class=""><a href="#" ng-click="rechercher(' + liste.page + 1 + ')"><i class="fa fa-angle-right"></i></a></li>');
        items.push('<li class=""><a href="#" ng-click="rechercher(' + liste.lastPage + ')"><i class="fa fa-angle-double-right"></i></a></li>');
        $('.pagination').append( items.join('') );
    }

    $scope.rechercherAvancee = function () {
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

        var formData = {
            'validation': parseInt($('select[name=validation]').val()),
            'etat': state,
            'validationMine': validationMine,
            'espece': $('input[name=espece]').val()
        };
        var dta = "validation=" + parseInt($('select[name=validation]').val()) + "&etat[]=" + state + "&validationMine=" + validationMine + "&espece=" + $('input[name=espece]').val();
        console.log(dta);

        $http({
            method: 'GET',
            url: 'findByespeceDwcAvancee?' + dta,
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function success(response) {
            $scope.liste = response.data;
            $scope.recherche = $scope.darwin.scientificname;
            paginer($scope.liste[0].total, 20, 1);
            $scope.recherchePagination = 0;
            $("#loader-spinner").hide();
        }, function error(response) {
            console.log(response.statusText);
            $("#loader-spinner").hide();
        });
    };
    $scope.rechercherMulti = function () {
        var etat = $('[name="etat[]"]');
        var state = [];
        var j = 0;
        for (var i = 0; i < etat.length; i++) {
            if (etat[i].checked == true) {
                state[j] = etat[i].value;
                j++;
            }
        }
        var validationMine = parseInt($('[name="validationMine"]').val());
        console.log(validationMine);
        if (isNaN(validationMine))
            validationMine = -999;

        var formData = {
            'validation': parseInt($('select[name=validation]').val()),
            'etat': state,
            'validationMine': validationMine,
            'espece': $('input[name=espece]').val()
        };
        $http({
            method: 'POST',
            url: 'findDwcMulticritere',
            data: formData,
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
//            dataType: 'json'
        }).then(function success(response) {
            $scope.liste = response.data;
            $("#loader-spinner").hide();
//            $scope.recherche=$scope.darwin.scientificname;
        }, function error(response) {
            console.log(response.statusText);
            $("#loader-spinner").hide();
        });
    };
    $scope.getColonnes = function () {
        var formData = {};
        $http({
            method: 'POST',
            url: 'getColonnesDwc',
            data: formData,
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
//            dataType: 'json'
        }).then(function success(response) {
            $scope.colonnes = response.data;
            $("#modal-liste-colonnes").modal({backdrop: 'static'});
        }, function error(response) {
            console.log(response.statusText);
        });
    };
    $scope.checkAll = function () {
        $('[name="col[]"]').prop('checked', $('#checkAll').is(":checked"));
    };
    $scope.getDwcCsv = function () {
        var col = $('[name="col[]"]');
        var data = "?";
        for (var i = 0; i < col.length; i++) {
            if (col[i].checked == true) {
                data = data + col[i].name + "=" + col[i].value + "&";
            }
        }
        //new change
        var page = $('#pageEnCours').val();
        var formData = new FormData();
        var dwcs = $scope.darwin.scientificname;
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
        if (isNaN(validationMine))
            validationMine = -999;

        var dta = "validation=" + parseInt($('select[name=validation]').val()) + "&etat[]=" + state + "&validationMine=" + validationMine + "&espece=" + $('input[name=espece]').val() + "&page=" + page;
        window.location = 'dwcCsv' + data + dta;
    }
    ;
    $scope.upload = function () {
        var formData = new FormData();
        var file = $('#csv-xl')[0].files[0];
        formData.append("excelfile", file);
        console.log(document.getElementById("publique").checked);
        if (document.getElementById("publique").checked) {
            formData.append("publique", 1);
        } else
            formData.append("publique", 0);
        $http({
            method: 'POST',
            url: 'processExcel',
//            enctype: 'multipart/form-data',
            data: formData,
            headers: {
                'Accept': 'application/json',
                'Content-Type': undefined
            }
        }).then(function success(response) {
            $scope.liste = response.data;
            $scope.recherche = $scope.darwin.scientificname;
            $('#modal-upload-dwc').modal('hide');
        }, function error(response) {
            $('#modal-alert').modal({backdrop: 'static'});
            $('#modal-upload-dwc').modal('hide');
        });
    };
//    $(document).ready(function () {
//        $('#form-search').submit(function (event) {
//            // get the form data
//            var formData = {
//                'validation': $('select[name=validation]').val(),
//                'chercheur': $('input[name=chercheur]').val()
//
//            };
//            // process the form
//            $.ajax({
//                type: 'POST',
//                url: 'findByespeceDwcs',
//                data: formData,
//                dataType: 'json',
//                success: function (data) {
//                    $scope.liste = data.data;
//                }
//            });
//            // stop the form from submitting and refreshing
//            event.preventDefault();
//        });
//    });


    $scope.editer = function (darwin) {
        $("#editOrnew").modal({backdrop: 'static'});
        $scope.form = angular.copy(darwin);
    };
    $scope.annuler = function () {
        $("#editOrnew").modal("hide");
        $scope.form = {};
    };
    $scope.save = function () {
        $http({
            method: 'POST',
            url: 'saveDwc',
            data: angular.toJson($scope.form),
            headers: {
                'Accept': 'application/json',
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
    $scope.delete = function (darwin) {
        $http({
            method: 'POST',
            url: 'deleteDwc',
            data: angular.toJson(darwin),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function success(response) {
            getall();
        }, function error(response) {
            console.log(response.statusText);
        });
    };

//    $scope.rechercher = function (page) {
//        var formData = new FormData();
////        var dwcs = angular.toJson($scope.darwin);                
////        var temp = dwcs.scientificName;
//        var dwcs = $scope.darwin.scientificname;
//        if (dwcs == undefined)
//            dwcs = null;
//        console.log(dwcs);
//        formData.append("dwcs", dwcs);
//        formData.append("page", page);
//        $http({
//            method: 'POST',
//            url: 'findByespeceDwcPaginated',
//            data: formData,
//            headers: {
//                'Accept': 'application/json',
//                'Content-Type': undefined
//            }
//        }).then(function success(response) {
//            $scope.liste = response.data;
//            paginer($scope.liste[0].total, 20, page);
//            $scope.recherche = $scope.darwin.scientificname;
//        }, function error(response) {
//            console.log(response.statusText);
//        });
//    };

    $scope.rechercher = function (page) {
        if ($scope.recherchePagination === 1) {
            $http({
                method: 'GET',
                url: 'rechercherGlobaleEspeceDcwPage?champ=' + $('#rechercheGlobale').val() + '&page=' + page,
                data: angular.toJson($scope.darwin),
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
            var dwcs = $scope.darwin.scientificname;
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
//            $scope.recherche = $scope.darwin.scientificname;
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
                data: angular.toJson($scope.darwin),
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
//        var dwcs = angular.toJson($scope.darwin);                
//        var temp = dwcs.scientificName;
            var dwcs = $scope.darwin.scientificName;
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
                $scope.recherche = $scope.darwin.scientificname;
            }, function error(response) {
                console.log(response.statusText);
            });
        }
    };

    $scope.uploadByLink = function () {
        $('#modal-upload_spinner').modal({backdrop: 'static'});
        $http({
            method: 'GET',
            url: 'uploadByLink?url=' + $('#link').val(),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function success(response) {
            console.log(response);
            getall();
            $('#modal-upload_spinner').modal('toggle');
        }, function error(response) {
            $('#modal-upload_spinner').modal('toggle');
            $('#modal-alert').modal({backdrop: 'static'});
        });
    };

    $scope.getalls = function () {
        $http({
            method: 'POST',
            url: 'findByespeceDwc',
            data: angular.toJson($scope.darwin),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function success(response) {
            $scope.liste = response.data;
            paginer($scope.liste[0].total, 20, 1);
//            $('#pageEnCours').val(1);
        }, function error(response) {
            console.log(response);
        });
    };

    $scope.rechercheGlobale = function () {
        $http({
            method: 'GET',
            url: 'rechercherGlobaleEspeceDcw?champ=' + $('#rechercheGlobale').val(),
            data: angular.toJson($scope.darwin),
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

