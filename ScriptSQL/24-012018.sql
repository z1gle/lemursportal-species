CREATE OR REPLACE VIEW public.vue_recherche_taxonomi AS 
 SELECT *, rtrim(ltrim(replace(taxonomi_base::text, ',', ''), '('), ')') as test FROM taxonomi_base;