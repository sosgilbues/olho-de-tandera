(ns olho-de-tandera.diplomat.http-server.transaction
  (:require [olho-de-tandera.adapters.transaction :as adapters.transaction]
            [olho-de-tandera.controllers.transaction :as controllers.transaction]
            [schema.core :as s]))

(s/defn create!
  [{{:keys [transaction]} :json-params
    {:keys [datomic]}     :components}]
  {:status 200
   :body   {:transaction (-> (adapters.transaction/wire->internal transaction)
                             (controllers.transaction/create! datomic)
                             adapters.transaction/internal->wire)}})
