(ns olho-de-tandera.adapters.transaction-test
  (:require [clojure.test :refer :all]
            [common-clj.test.helper.schema :as test.helper.schema]
            [java-time.api :as jt]
            [matcher-combinators.test :refer [match?]]
            [olho-de-tandera.adapters.transaction :as adapters.transaction]
            [olho-de-tandera.models.transaction :as models.transaction]
            [olho-de-tandera.wire.datomic.transaction :as wire.database.transaction]
            [olho-de-tandera.wire.in.transaction :as wire.in.transaction]
            [schema.test :as s]))

(def wire-transaction
  (test.helper.schema/generate wire.in.transaction/Transaction
                               {:reference-date "2024-01-01"
                                :type           "debit"
                                :amount         100.0}))

(def internal-transaction
  (test.helper.schema/generate models.transaction/Transaction
                               {:transaction/reference-date (jt/local-date "2024-01-01")
                                :transaction/type           :debit
                                :transaction/amount         100.0M}))

(def database-transaction
  (test.helper.schema/generate wire.database.transaction/Transaction
                               {:transaction/type   :debit
                                :transaction/amount 100.0M}))

(s/deftest wire->internal-test
  (testing "That the wire->internal function converts a wire transaction to an internal transaction"
    (is (match? {:transaction/id             uuid?
                 :transaction/reference-date jt/local-date?
                 :transaction/created-at     jt/local-date-time?
                 :transaction/type           :debit
                 :transaction/title          string?
                 :transaction/description    string?
                 :transaction/amount         100.0M}
                (adapters.transaction/wire->internal wire-transaction)))))

(s/deftest internal->wire-test
  (testing "That the internal->wire function converts an internal transaction to a wire transaction"
    (is (match? {:id             string?
                 :reference-date "2024-01-01"
                 :title          string?
                 :description    string?
                 :type           "debit"
                 :amount         100.0M}
                (adapters.transaction/internal->wire internal-transaction)))))

(s/deftest internal->database-test
  (testing "That the internal->database function converts an internal transaction to a database transaction"
    (is (match? {:transaction/id             uuid?
                 :transaction/reference-date inst?
                 :transaction/title          string?
                 :transaction/description    string?
                 :transaction/created-at     inst?
                 :transaction/amount         100.0M
                 :transaction/type           :debit}
                (adapters.transaction/internal->database internal-transaction)))))

(s/deftest database->internal-test
  (testing "That the database->internal function converts a database transaction to an internal transaction"
    (is (match? #:transaction{:id             uuid?
                              :reference-date jt/local-date?
                              :title          string?
                              :description    string?
                              :created-at     jt/local-date-time?
                              :amount         100.0M
                              :type           :debit}
                (adapters.transaction/database->internal database-transaction)))))
