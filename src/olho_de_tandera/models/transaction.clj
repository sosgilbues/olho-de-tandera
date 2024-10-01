(ns olho-de-tandera.models.transaction
  (:require [schema.core :as s])
  (:import (java.time LocalDate LocalDateTime)))

(def transaction-types #{:debit :credit})

(def TransactionType (apply s/enum transaction-types))

(def transaction
  {:transaction/id             s/Uuid
   :transaction/reference-date LocalDate
   :transaction/title          s/Str
   :transaction/description    s/Str
   :transaction/created-at     LocalDateTime
   :transaction/amount         s/Num
   :transaction/type           TransactionType})

(s/defschema Transaction transaction)
