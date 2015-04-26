(ns suurvay.storage-test
  (:require [suurvay.storage.in-memory :refer :all]
            [suurvay.storage :refer :all]
            [suurvay.storage.postgres :refer [pg-storage]
             :as pg]
            [suurvay.twitter-rest-test :refer [test-creds]]
            [suurvay.twitter-rest :as tw]
            [clojure.java.jdbc :as jdbc]
            [clojure.test :refer :all]))

(def me (tw/get-user test-creds "postpunkjustin"))
(def my-timeline (tw/get-timeline-details test-creds "postpunkjustin"))

(defn test-storage-instance
  "Runs a standard set of tests on the given instance of the Storage
  protocol."
  [stg]
  (testing "can store and retrieve a user"
    (is (nil? (store-user stg me)))
    (let [retrieved (get-user stg (:id me))]
      (is (= me retrieved))))

  (testing "does not throw an error on duplicate users"
    (is (nil? (store-user stg me))))

  (testing "can store and retrieve a status object"
    (let [status (first my-timeline)]
      (is (nil? (store-status stg status)))
      (let [retrieved (get-status stg (:id status))]
        (is (= status retrieved)))

      (testing "and does not throw an error on duplicate status objects"
        (is (nil? (store-status stg status))))))

  (testing "can store and retrieve a timeline"
    (is (nil? (store-timeline stg my-timeline)))
    (let [retrieved (get-timeline stg (:id me))]
      (is (= (set my-timeline)
             (set retrieved)))))

  (testing "storing statuses is idempotent"
    (is (nil? (store-timeline stg my-timeline)))
    (let [retrieved (get-timeline stg (:id me))]
      (is (= 200 (count retrieved))))))

(deftest in-memory-storage-test
  (testing "an in-memory storage instance"
    (test-storage-instance (in-memory-only))))

(def test-db
  {:subprotocol "postgresql"
   :subname "suurvay_test"
   :user "suurvay_test"
   :password "suurvay_test"})

(def pgstg (pg-storage test-db))

(deftest pg-storage-test
  (jdbc/execute! test-db ["DELETE FROM statuses"])
  (jdbc/execute! test-db ["DELETE FROM twitter_users"])
  (testing "a postgres-backed storage instance"
    (test-storage-instance (pg-storage test-db))))
