(ns olho-de-tandera.db.datomic.config
  (:require [olho-de-tandera.wire.datomic.transaction :as wire.database.transaction]))


(def schemas (concat []
                     wire.database.transaction/transaction))
