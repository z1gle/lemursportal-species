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
    $scope.downloadInformation = {};
    $scope.recherche = "";
    $scope.file = {};
    $scope.pages = [];
    $scope.recherchePagination = 0;
    $scope.pageEnCours = 0;
    $scope.lastPage = 0;
    var total = [];
    rechercherAvancee();

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
            $("#loader-spinner").hide();
            paginer($scope.liste[0].total, 20, 1);
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

    $scope.rechercherAvancee = function () {
        $('#rechercheGlobale').val('');
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
    function rechercherAvancee() {
        $('#rechercheGlobale').val('');
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
    }
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

    $scope.checkObligatory = function (id, cle) {
        if ($('#' + id).val() == '') {
            $('#' + id).css("border-color", "red");
            total[cle] = false;
        } else {
            $('#' + id).css("border-color", "#ececec");
            total[cle] = true;
        }
        for (var i = 0; i < total.length; i++) {
            if (total[i] == false) {
                console.log(i + total[i]);
                $('#download-continue').prop("disabled", true);
                break;
            }
            if (i == 3) {
                $('#download-continue').prop("disabled", false);
            }
        }
    };

    $scope.getColonnes = function () {
        var liste = [];
        liste = $('.must-not-empty');
        var i;
        for (i = 0; i < liste.length; i++) {
            if (liste[i].value == '') {
                break;
            }
        }
        if (i > liste.length - 1) {
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
        }
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

        $http({
            method: 'POST',
            url: 'downloadInformations?' + data + dta,
            data: angular.toJson($scope.downloadInformation),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function success(response) {
            if (response.data == true) {
                window.location = 'dwcCsv' + data + dta;
            } else {
                console.log('error');
            }
        }, function error(response) {
            console.log(response.statusText);
        });
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
        if ($('#sep').val() == 0) {
            formData.append("sep", 0);
        }
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
            if (response.data[0].message != null) {
                console.log(response.data[0].message);
                $('#messageAlerte').text(response.data[0].message);
                $('#modal-upload_spinner').modal('hide');
                $('#modal-alert').modal({backdrop: 'static'});
            } else {
                $scope.liste = response.data;
                $scope.recherche = $scope.darwin.scientificname;
                $('#modal-upload_spinner').modal('hide');
            }
        }, function error(response) {
            $('#messageAlerte').text('Un erreur est survenu lors du t�l�chargement. Veuiller v�rifier votre acr�ditation ou la structure des donn�es.');
            $('#modal-alert').modal({backdrop: 'static'});
            $('modal-upload_spinner').modal('hide');
        });
    };

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

