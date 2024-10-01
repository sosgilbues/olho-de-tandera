(ns olho-de-tandera.controllers.transaction
  (:require [olho-de-tandera.db.datomic.transaction :as database.transaction]
            [olho-de-tandera.models.transaction :as models.transaction]
            [datomic.api :as d]
            [schema.core :as s]))

(s/defn create! :- models.transaction/Transaction
  [transaction :- models.transaction/Transaction
   datomic]
  (database.transaction/insert! transaction datomic))

(s/defn fetch-all :- [models.transaction/Transaction]
  [datomic]
  (database.transaction/lookup-all (d/db datomic)))
