(ns olho-de-tandera.db.datomic.transaction
  (:require [common-clj.integrant-components.datomic :as component.datomic]
            [olho-de-tandera.adapters.transaction :as adapters.transaction]
            [olho-de-tandera.models.transaction :as models.transaction]
            [schema.core :as s]))

(s/defn insert! :- models.transaction/Transaction
  [transaction :- models.transaction/Transaction
   datomic]
  (-> (component.datomic/transact-and-lookup-entity! :transaction/id (adapters.transaction/internal->database transaction) datomic)
      :entity
      adapters.transaction/database->internal))
