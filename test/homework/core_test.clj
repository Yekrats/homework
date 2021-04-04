(ns homework.core-test
  (:require [clojure.test :refer :all]
            [homework.core :refer :all]))

(deftest basic-parse
  (testing
    (is (= (parse-line "Starkey | Scott | name@gmail.com | green | 01/01/2000") ; pipe-delimited data
           {:last-name "Starkey", :first-name "Scott", :email "name@gmail.com", :favorite-color "green", :date-of-birth #inst "2000-01-01T05:00:00.000-00:00"}))

    (is (= (parse-line "Starkey, Scott, name@gmail.com, green, 01/01/2000") ; comma-delimited data
           {:last-name "Starkey", :first-name "Scott", :email "name@gmail.com", :favorite-color "green", :date-of-birth #inst "2000-01-01T05:00:00.000-00:00"}))

    (is (= (parse-line "Starkey Scott name@gmail.com green 01/01/2000") ; space-delimited data
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
              (parse-file "./resources/comma-data.csv")])))

(deftest date-asc
  (testing
      (is [= '({:last-name "Gruber",
                :first-name "Hans",
                :email "hans@volksfrei.de",
                :favorite-color "blue",
                :date-of-birth #inst "1958-02-14T06:00:00.000-00:00"}
              {:last-name "Gennero",
                :first-name "Holly",
                :email "gennero@nakatomi.com",
                :favorite-color "yellow",
                :date-of-birth #inst "1960-06-20T05:00:00.000-00:00"}
              {:last-name "McClane",
                :first-name "John",
                :email "jmcclane@nypd.com",
                :favorite-color "brown",
                :date-of-birth #inst "1960-12-24T05:00:00.000-00:00"}
              {:last-name "Starkey",
                :first-name "Scotticus",
                :email "yekrats@gmail.com",
                :favorite-color "green",
                :date-of-birth #inst "2000-01-01T05:00:00.000-00:00"})
           (sort-by-birthdate-asc (parse-file "./resources/comma-data.csv"))])))

(deftest email-desc
  (testing
      (is [= '({:last-name "Stark",
                :first-name "Tony",
                :email "tony@stark.com",
                :favorite-color "red",
                :date-of-birth #inst "1988-02-29T05:00:00.000-00:00"}
              {:last-name "AStarkey",
                :first-name "Scotto",
                :email "scotto@yekrats.com",
                :favorite-color "turquoise",
                :date-of-birth #inst "1999-01-01T05:00:00.000-00:00"}
              {:last-name "BStarkey",
                :first-name "Scotto",
                :email "scotto@yekrats.com",
                :favorite-color "blue",
                :date-of-birth #inst "1998-01-01T05:00:00.000-00:00"}
              {:last-name "CStarkey",
                :first-name "Scotto",
                :email "scotto@yekrats.com",
                :favorite-color "chartreuse",
                :date-of-birth #inst "1997-01-01T05:00:00.000-00:00"}
              {:last-name "Banner",
                :first-name "Bruce",
                :email "bruce_b@avengers.org",
                :favorite-color "green",
                :date-of-birth #inst "1996-04-10T05:00:00.000-00:00"})
            (sort-by-email-desc (parse-file "./resources/weird-space-same-email-case.txt"))])))
