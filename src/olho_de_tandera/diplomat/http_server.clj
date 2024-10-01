(ns olho-de-tandera.diplomat.http-server
  (:require [common-clj.traceability.core :as common-traceability]
            [olho-de-tandera.diplomat.http-server.transaction :as diplomat.http-server.transaction]))

(def routes [["/api/transactions"
              :post [(common-traceability/http-with-correlation-id diplomat.http-server.transaction/create!)]
              :route-name :create-transaction]
             ["/api/transactions"
              :get [(common-traceability/http-with-correlation-id diplomat.http-server.transaction/fetch-all)]
              :route-name :fetch-all-transactions]])
