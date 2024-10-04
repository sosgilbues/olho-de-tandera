(ns olho-de-tandera.db.datomic.transaction-test
  (:require [clojure.test :refer :all]
            [common-clj.integrant-components.datomic :as component.datomic]
            [common-clj.test.helper.schema :as test.helper.schema]
            [datomic.api :as d]
            [java-time.api :as jt]
            [matcher-combinators.test :refer [match?]]
            [olho-de-tandera.db.datomic.config :as database.config]
            [olho-de-tandera.db.datomic.transaction :as database.transaction]
            [olho-de-tandera.models.transaction :as models.transaction]
            [schema.test :as s]))

(def transaction-id (random-uuid))

(def internal-transaction
  (test.helper.schema/generate models.transaction/Transaction
                               {:transaction/id             transaction-id
                                :transaction/reference-date (jt/local-date "2024-01-01")
                                :transaction/type           :debit
                                :transaction/amount         100.0M}))

(s/deftest insert-test
  (let [database-conn (component.datomic/mocked-datomic database.config/schemas)]
    (testing "Should insert a transaction"
      (is (match? {:transaction/id   uuid?
                   :transaction/type :debit}
                  (database.transaction/insert! internal-transaction database-conn))))))

(deftest lookup-all-test
  (let [datomic (component.datomic/mocked-datomic database.config/schemas)]

    (database.transaction/insert! internal-transaction datomic)

    (database.transaction/insert! (test.helper.schema/generate models.transaction/Transaction
                                                               {:transaction/reference-date (jt/local-date "2023-01-01")
                                                                :transaction/type           :credit
                                                                :transaction/amount         200.0M}) datomic)

    (testing "Should insert a transaction"
      (is (match? [{:transaction/id   uuid?
                    :transaction/type :debit}
                   {:transaction/id   uuid?
                    :transaction/type :credit}]
                  (database.transaction/lookup-all (d/db datomic)))))))

(s/deftest lookup-test
  (let [datomic (component.datomic/mocked-datomic database.config/schemas)]

    (database.transaction/insert! internal-transaction datomic)

    (testing "Should be able to query transaction by ID"
      (is (match? {:transaction/id   transaction-id
                   :transaction/type :debit}
                  (database.transaction/lookup transaction-id (d/db datomic)))))))
