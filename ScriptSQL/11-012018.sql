ALTER TABLE validation_darwin_core
  ADD validationexpert boolean,
  ADD idexpert integer REFERENCES utilisateur,
  ADD datevalidation timestamp;