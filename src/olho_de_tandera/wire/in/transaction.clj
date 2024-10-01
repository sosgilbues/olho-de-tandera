(ns olho-de-tandera.wire.in.transaction
  (:require [olho-de-tandera.models.transaction :as models.transaction]
            [schema.core :as s]))

(def transaction-types (map name models.transaction/transaction-types))
(def TransactionTypes (apply s/enum transaction-types))

(s/defschema MaturityDate
  "Example: '2024-09-07'"
  (s/constrained s/Str #(re-matches #"^\d{4}-\d{2}-\d{2}$" %)))

;TODO: Create string validate types for string dates and date-times
(def transaction
  {:reference-date MaturityDate
   :title          s/Str
   :description    s/Str
   :amount         s/Num
   :type           TransactionTypes})

(s/defschema Transaction transaction)
