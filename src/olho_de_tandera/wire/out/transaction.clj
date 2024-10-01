(ns olho-de-tandera.wire.out.transaction
  (:require [olho-de-tandera.models.transaction :as models.transaction]
            [schema.core :as s]))

(def transaction-types (map name models.transaction/transaction-types))
(def TransactionTypes (apply s/enum transaction-types))

;TODO: Create string validate types for string dates and date-times
(def transaction
  {:id             s/Str
   :reference-date s/Str
   :title          s/Str
   :description    s/Str
   :amount         s/Num
   :type           TransactionTypes})

(s/defschema Transaction transaction)

(s/defschema TransactionDocument {:transaction Transaction})
