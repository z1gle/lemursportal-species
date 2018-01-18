ALTER TABLE validation_darwin_core
  DROP IF EXISTS validationexpert,
  ADD validationexpert integer;

CREATE OR REPLACE VIEW public.vue_validation_darwin_core AS 
 SELECT u.nom,
    u.prenom,
    d.iddwc,
    d.institutioncode,
    d.collectioncode,
    d.datasetname,
    d.ownerinstitutioncode,
    d.basisofrecord,
    d.informationwithheld,
    d.datageneralizations,
    d.dynamicproperties,
    d.scientificname,
    d.acceptednameusage,
    d.higherclassification,
    d.kingdom,
    d.phylum,
    d.darwinclass,
    d.darwinorder,
    d.family,
    d.genus,
    d.subgenus,
    d.specificepithet,
    d.infraspecificepithet,
    d.taxonrank,
    d.verbatimtaxonrank,
    d.scientificnameauthorship,
    d.vernacularname,
    d.nomenclaturalcode,
    d.taxonremarks,
    d.catalognumber,
    d.occurrenceremarks,
    d.recordnumber,
    d.recordedby,
    d.individualcount,
    d.sex,
    d.lifestage,
    d.reproductivecondition,
    d.behavior,
    d.preparations,
    d.disposition,
    d.othercatalognumbers,
    d.previousidentifications,
    d.associatedmedia,
    d.associatedreferences,
    d.associatedoccurrences,
    d.associatedsequences,
    d.associatedtaxa,
    d.samplingprotocol,
    d.samplingeffort,
    d.eventdate,
    d.eventtime,
    d.startdayofyear,
    d.enddayofyear,
    d.dwcyear,
    d.dwcmonth,
    d.dwcday,
    d.verbatimeventdate,
    d.habitat,
    d.fieldnumber,
    d.fieldnotes,
    d.eventremarks,
    d.highergeography,
    d.continent,
    d.waterbody,
    d.islandgroup,
    d.island,
    d.country,
    d.countrycode,
    d.stateprovince,
    d.county,
    d.municipality,
    d.locality,
    d.verbatimlocality,
    d.verbatimelevation,
    d.minimumelevationinmeters,
    d.maximumelevationinmeters,
    d.verbatimdepth,
    d.minimumdepthinmeters,
    d.maximumdepthinmeters,
    d.minimumdistanceabovesurfaceinmeters,
    d.maximumdistanceabovesurfaceinmeters,
    d.locationaccordingto,
    d.locationremarks,
    d.verbatimcoordinates,
    d.verbatimlatitude,
    d.verbatimlongitude,
    d.verbatimcoordinatesystem,
    d.verbatimsrs,
    d.decimallatitude,
    d.decimallongitude,
    d.geodeticdatum,
    d.coordinateuncertaintyinmeters,
    d.coordinateprecision,
    d.pointradiusspatialfit,
    d.footprintwkt,
    d.footprintsrs,
    d.footprintspatialfit,
    d.georeferencedby,
    d.georeferenceprotocol,
    d.georeferencesources,
    d.georeferenceverificationstatus,
    d.georeferenceremarks,
    d.identifiedby,
    d.dateidentified,
    d.identificationreferences,
    d.identificationremarks,
    d.identificationqualifier,
    d.typestatus,
    d.idutilisateurupload,
    v.id,
    v.iddarwincore,
    v.gps,
    v.annee,
    v.collecteur,
    v.accepted_speces,
    v.validationexpert
   FROM utilisateur u
     JOIN darwin_core d ON d.idutilisateurupload = u.id
     JOIN validation_darwin_core v ON v.iddarwincore = d.iddwc;