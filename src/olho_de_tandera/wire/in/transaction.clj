(ns olho-de-tandera.wire.in.transaction
  (:require [olho-de-tandera.models.transaction :as models.transaction]
            [schema.core :as s]))

(def transaction-types (map str models.transaction/transaction-types))
(def TransactionTypes (apply s/enum transaction-types))

;TODO: Create string validate types for string dates and date-times
(def transaction
  {:reference-date s/Str
   :title          s/Str
   :description    s/Str
   :type           TransactionTypes})

(s/defschema Transaction transaction)
