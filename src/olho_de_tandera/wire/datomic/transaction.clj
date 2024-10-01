(ns olho-de-tandera.wire.datomic.transaction
  (:require [olho-de-tandera.models.transaction :as models.transaction]
            [schema.core :as s])
  (:import (java.util Date)))

(def transaction
  [{:db/ident       :transaction/id
    :db/valueType   :db.type/uuid
    :db/cardinality :db.cardinality/one
    :db/unique      :db.unique/identity
    :db/doc         "Transaction ID"}
   {:db/ident       :transaction/reference-date
    :db/valueType   :db.type/instant
    :db/cardinality :db.cardinality/one
    :db/doc         "Transaction reference date"}
   {:db/ident       :transaction/title
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/one
    :db/doc         "Transaction title"}
   {:db/ident       :transaction/description
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/one
    :db/doc         "Transaction description"}
   {:db/ident       :transaction/created-at
    :db/valueType   :db.type/instant
    :db/cardinality :db.cardinality/one
    :db/doc         "Transaction creation date"}
   {:db/ident       :transaction/type
    :db/valueType   :db.type/keyword
    :db/cardinality :db.cardinality/one
    :db/doc         "Transaction type"}])

(s/defschema Transaction
  (assoc models.transaction/Transaction
         :transaction/created-at Date
         :transaction/reference-date Date))
