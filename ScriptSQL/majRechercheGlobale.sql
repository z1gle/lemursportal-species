create view darwinCoreRechercheGlobale as
select vue.*, recherche.champ
from vue_validation_darwin_core as vue 
join vue_recherche_darwin_core as recherche
on vue.iddwc = recherche.iddwc;