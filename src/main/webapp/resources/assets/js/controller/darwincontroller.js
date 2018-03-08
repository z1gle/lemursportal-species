/* ﻿
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
app.controller("darwin", function ($scope, $http) {
    $scope.liste = [];
    $scope.familles = [];
    $scope.colonnes = [];
    $scope.darwin = {};
    $scope.recherche = "";
    $scope.file = {};
    $scope.pages = [];
    getall();
    getFamille();

    function getFamille() {
        $http({
            method: 'POST',
            url: 'getFamilleDwc',
            data: angular.toJson($scope.darwin),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function success(response) {
            $scope.familles = response.data;
        }, function error(response) {
            console.log(response);
        });
    }

    function getall() {
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
            $("#loader-spinner").hide();
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
        $('#pageFin').val(temp);
    }

    $scope.rechercher = function () {
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
            $scope.recherche = $scope.darwin.scientificname;
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
        if(isNaN(validationMine)) validationMine = -999;

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
                console.log(data);
            }
        }
        var validation = $('select[name=validation]').val();
        var chercheur = $('input[name=chercheur]').val();
        data = data + "validation=" + validation + "&chercheur=" + chercheur;
        window.location = 'dwcCsv' + data;
    }
    ;
    $scope.upload = function () {
        var formData = new FormData();
        var file = $('#csv-xl')[0].files[0];
        formData.append("excelfile", file);
        console.log(document.getElementById("publique").checked);
        if(document.getElementById("publique").checked) {
            formData.append("publique", 1);
        } else formData.append("publique", 0);
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

    $scope.rechercher = function (page) {
        var formData = new FormData();
//        var dwcs = angular.toJson($scope.darwin);                
//        var temp = dwcs.scientificName;
        var dwcs = $scope.darwin.scientificname;
        if (dwcs == undefined)
            dwcs = null;
        console.log(dwcs);
        formData.append("dwcs", dwcs);
        formData.append("page", page);
        $http({
            method: 'POST',
            url: 'findByespeceDwcPaginated',
            data: formData,
            headers: {
                'Accept': 'application/json',
                'Content-Type': undefined
            }
        }).then(function success(response) {
            $scope.liste = response.data;
            paginer($scope.liste[0].total, 20, page);
            $scope.recherche = $scope.darwin.scientificname;
        }, function error(response) {
            console.log(response.statusText);
        });
    };

    $scope.rechercherFin = function () {
        var formData = new FormData();
//        var dwcs = angular.toJson($scope.darwin);                
//        var temp = dwcs.scientificName;
        var dwcs = $scope.darwin.scientificName;
        if (dwcs == undefined)
            dwcs = null;
        console.log(dwcs);
        console.log($('#pageFin').val());
        formData.append("dwcs", dwcs);
        formData.append("page", $('#pageFin').val());
        $http({
            method: 'POST',
            url: 'findByespeceDwcPaginated',
            data: formData,
            headers: {
                'Accept': 'application/json',
                'Content-Type': undefined
            }
        }).then(function success(response) {
            $scope.liste = response.data;
            paginer($scope.liste[0].total, 20, $('#pageFin').val());
            $scope.recherche = $scope.darwin.scientificname;
        }, function error(response) {
            console.log(response.statusText);
        });
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

    $scope.getGenre = function (famille) {
        if (document.getElementById(famille).checked) {
            $.ajax({
                url: 'getGenreDwc?famille=' + famille,
                dataType: 'json',
                success: function (json) {
                    var option = "";
                    for (var i = 0; i < json.length; i++) {
                        option = option + "<div class=\"row\">";
                        option = option + "<li>" + '<input name="value[]" type="checkbox" onclick="getEspece(\'' + famille + '\',\'' + json[i] + '\')" value="typeGenre-famille-' + famille + '-genre-' + json[i] + '" id="typeGenre-famille-' + famille + '-genre-' + json[i] + '" >';
                        option = option + json[i];
                        option = option + "</li></div>";
                        option = option + "<div><ul style=\"list-style-type:none;\" id=\"espece-" + json[i] + "\"></ul></div></div>";
                    }
                    $("#genre-" + famille).append(option).trigger('change');
                }
            });
        } else {
            var option = "";
            $("#genre-" + famille).html('<ul id="genre-{{famille}}"></ul>');
        }
    };
    
    $scope.getalls = function() {
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
        }, function error(response) {
            console.log(response);
        });
    };

});

