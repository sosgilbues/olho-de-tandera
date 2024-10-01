(ns olho-de-tandera.adapters.transaction
  (:require [java-time.api :as jt]
            [olho-de-tandera.models.transaction :as models.transaction]
            [olho-de-tandera.wire.datomic.transaction :as wire.database.transaction]
            [olho-de-tandera.wire.in.transaction :as wire.in.transaction]
            [olho-de-tandera.wire.out.transaction :as wire.out.transaction]
            [schema.core :as s]))

(s/defn wire->internal :- models.transaction/Transaction
  [{:keys [reference-date type title description]} :- wire.in.transaction/Transaction]
  {:transaction/id             (random-uuid)
   :transaction/reference-date (jt/local-date reference-date)
   :transaction/created-at     (jt/local-date-time)
   :transaction/type           (keyword type)
   :transaction/title          title
   :transaction/description    description})

(s/defn internal->wire :- wire.out.transaction/Transaction
  [{:transaction/keys [id reference-date title description type]} :- wire.in.transaction/Transaction]
  {:id             (str id)
   :reference-date (str reference-date)
   :title          title
   :description    description
   :type           (str type)})

(s/defn internal->database :- wire.database.transaction/Transaction
  [{:transaction/keys [created-at reference-date] :as transaction} :- models.transaction/Transaction]
  (assoc transaction
         :transaction/created-at (-> (jt/zoned-date-time created-at (jt/zone-id "UTC"))
                                     jt/java-date)
         :transaction/reference-date (-> (jt/zoned-date-time reference-date (jt/zone-id "UTC"))
                                         jt/java-date)))

(s/defn database->internal :- models.transaction/Transaction
  [{:transaction/keys [created-at reference-date] :as transaction} :- wire.database.transaction/Transaction]
  (assoc transaction
         :transaction/reference-date (-> (jt/zoned-date-time reference-date (jt/zone-id "UTC"))
                                         jt/local-date)
         :transaction/created-at (-> (jt/zoned-date-time created-at (jt/zone-id "UTC"))
                                     jt/local-date-time)))
