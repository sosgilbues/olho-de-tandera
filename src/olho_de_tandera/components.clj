(ns olho-de-tandera.components
  (:require [common-clj.integrant-components.aws-auth]
            [common-clj.integrant-components.config]
            [common-clj.integrant-components.datomic]
            [common-clj.integrant-components.http-client]
            [common-clj.integrant-components.new-relic]
            [common-clj.integrant-components.prometheus]
            [common-clj.integrant-components.routes]
            [common-clj.integrant-components.service]
            [integrant.core :as ig]
            [olho-de-tandera.db.datomic.config :as database.config]
            [olho-de-tandera.diplomat.http-server :as diplomat.http-server]
            [taoensso.timbre :as timbre])
  (:gen-class))

(def config
  {:common-clj.integrant-components.aws-auth/aws-auth       {:components {:config (ig/ref :common-clj.integrant-components.config/config)}}
   :common-clj.integrant-components.config/config           {:path "resources/config.edn"
                                                             :env  :prod}
   :common-clj.integrant-components.datomic/datomic         {:schemas    database.config/schemas
                                                             :components {:config (ig/ref :common-clj.integrant-components.config/config)}}
   :common-clj.integrant-components.prometheus/prometheus   {:metrics []}
   :common-clj.integrant-components.http-client/http-client {:components {:config     (ig/ref :common-clj.integrant-components.config/config)
                                                                          :prometheus (ig/ref :common-clj.integrant-components.prometheus/prometheus)}}
   :common-clj.integrant-components.new-relic/new-relic     {:components {:config      (ig/ref :common-clj.integrant-components.config/config)
                                                                          :http-client (ig/ref :common-clj.integrant-components.http-client/http-client)}}
   :common-clj.integrant-components.routes/routes           {:routes diplomat.http-server/routes}
   :common-clj.integrant-components.service/service         {:components {:prometheus  (ig/ref :common-clj.integrant-components.prometheus/prometheus)
                                                                          :config      (ig/ref :common-clj.integrant-components.config/config)
                                                                          :routes      (ig/ref :common-clj.integrant-components.routes/routes)
                                                                          :http-client (ig/ref :common-clj.integrant-components.http-client/http-client)
                                                                          :datomic     (ig/ref :common-clj.integrant-components.datomic/datomic)}}})

(defn start-system! []
  (timbre/set-min-level! :info)
  (ig/init config))

(def -main start-system!)

(def config-test
  (-> config
      (assoc :common-clj.integrant-components.config/config {:path "resources/config.example.edn"
                                                             :env  :test})))
