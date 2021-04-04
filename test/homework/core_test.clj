(ns homework.core-test
  (:require [clojure.test :refer :all]
            [homework.core :refer :all]))

(deftest basic-parse
  (testing
    (is (= (select-keys (parse-line "Starkey | Scott | name@gmail.com | green | 01/01/2000") [:last-name :first-name :email :favorite-color :date-of-birth]) ; pipe-delimited data
           {:last-name "Starkey", :first-name "Scott", :email "name@gmail.com", :favorite-color "green", :date-of-birth #inst "2000-01-01T05:00:00.000-00:00"}))

    (is (= (select-keys (parse-line "Starkey, Scott, name@gmail.com, green, 01/01/2000") [:last-name :first-name :email :favorite-color :date-of-birth]) ; comma-delimited data
           {:last-name "Starkey", :first-name "Scott", :email "name@gmail.com", :favorite-color "green", :date-of-birth #inst "2000-01-01T05:00:00.000-00:00"}))

    (is (= (select-keys  (parse-line "Starkey Scott name@gmail.com green 01/01/2000") [:last-name :first-name :email :favorite-color :date-of-birth]) ; space-delimited data
           {:last-name "Starkey", :first-name "Scott", :email "name@gmail.com", :favorite-color "green", :date-of-birth #inst "2000-01-01T05:00:00.000-00:00"}))
    ))

 (deftest file-parse
   (testing
       (is [= '({:last-name "Starkey",
                  :first-name "Scotticus",
                  :email "yekrats@gmail.com",
                  :favorite-color "green",
                  :date-of-birth #inst "2000-01-01T05:00:00.000-00:00"}
                {:last-name "McClane",
                  :first-name "John",
                  :email "jmcclane@nypd.com",
                  :favorite-color "brown",
                  :date-of-birth #inst "1960-12-24T05:00:00.000-00:00"}
                {:last-name "Gennero",
                  :first-name "Holly",
                  :email "gennero@nakatomi.com",
                  :favorite-color "yellow",
                  :date-of-birth #inst "1960-06-20T05:00:00.000-00:00"}
                {:last-name "Gruber",
                  :first-name "Hans",
                  :email "hans@volksfrei.de",
                  :favorite-color "blue",
                  :date-of-birth #inst "1958-02-14T06:00:00.000-00:00"})
            (map #(select-keys % [:last-name :first-name :email :favorite-color :date-of-birth])
                 (parse-files "./resources/comma-data.csv"))])))

(deftest date-asc
  (testing
      (is [= '({:first-name "Hans", :date-of-birth #inst "1958-02-14T06:00:00.000-00:00"}
               {:first-name "Holly", :date-of-birth #inst "1960-06-20T05:00:00.000-00:00"}
               {:first-name "John", :date-of-birth #inst "1960-12-24T05:00:00.000-00:00"}
               {:first-name "Scotticus", :date-of-birth #inst "2000-01-01T05:00:00.000-00:00"})
           (map #(select-keys % [:first-name :date-of-birth])
                (sort-by-birthdate-asc (parse-files "./resources/comma-data.csv")))])))

(deftest email-desc
  (testing
      (is [= '({:last-name "Stark", :email "tony@stark.com"}
               {:last-name "AStarkey", :email "scotto@yekrats.com"}
               {:last-name "BStarkey", :email "scotto@yekrats.com"}
               {:last-name "CStarkey", :email "scotto@yekrats.com"}
               {:last-name "Banner", :email "bruce_b@avengers.org"})
           (map #(select-keys % [:last-name :email])
                (sort-by-email-desc (parse-files "./resources/weird-space-same-email-case.txt")))
           ])))

(deftest parse-multiple-files
  (testing
      (is [= '({:last-name "Starkey"}
               {:last-name "McClane"}
               {:last-name "Gennero"}
               {:last-name "Gruber"}
               {:last-name "Starkey"}
               {:last-name "Jordan"}
               {:last-name "Bunny"}
               {:last-name "Swackhammer"})
           (map #(select-keys % [:last-name])
                (parse-files "./resources/comma-data.csv" "./resources/space-data.txt"))])))
