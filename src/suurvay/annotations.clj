(ns suurvay.annotations
  "Contains annotations for functions *outside* of this project."
  (:require [clojure.core.typed :as t :refer
             [Any Bool Fn HMap HVec IFn Int Keyword Map Num Option Seq Str
              U Val Vec ann defalias]]))

(ann ^:no-check suurvay.twitter-rest/get-all-blocks [-> (Vec Int)])

(ann ^:no-check suurvay.twitter-rest/block! [(U Str Int) -> true])

(defalias Coordinates
  (HMap
   :mandatory
   {:coordinates (HVec [Num Num])
    :type (Val "Point")}
   :complete? true))

(defalias URLEntity
  (HMap
   :mandatory
   {:url Str
    :expanded_url Str
    :display_url Str
    :indices (Vec Int)}
   :complete? true))

(defalias HashTagEntity
  (HMap
   :mandatory
   {:text Str
    :indices (Vec Int)}
   :complete? true))

(defalias MentionsEntity
  (HMap
   :mandatory
   {:screen_name Str
    :name Str
    :id Int
    :id_str Str
    :indices (Vec Int)}
   :complete? true))

(defalias Entities
  (HMap :mandatory
        {:urls (Vec URLEntity)
         :hashtags (Vec HashTagEntity)
         :user_mentions (Vec MentionsEntity)}))

(defalias UserAbbrev
  (HMap
   :mandatory
   {:profile_sidebar_fill_color Str
    :profile_sidebar_border_color Str
    :profile_background_tile Bool
    :name Str
    :profile_image_url Str
    :created_at Str
    :location Str
    :follow_request_sent Bool
    :profile_link_color Str
    :is_translator Bool
    :id_str Str
    :entities Entities,
    :default_profile Bool
    :contributors_enabled Bool
    :favourites_count Int
    :url Str
    :profile_image_url_https Str
    :utc_offset Int
    :id Int
    :profile_use_background_image Bool
    :listed_count Int
    :profile_text_color Str
    :lang Str
    :followers_count Int
    :protected Bool
    :notifications (Option Bool) ;; deprecated
    :profile_background_image_url_https Str
    :profile_background_color Str
    :verified Bool
    :geo_enabled Bool
    :time_zone Str
    :description Str
    :default_profile_image Bool
    :profile_background_image_url Str
    :statuses_count Int
    :friends_count Int
    :following Bool
    :show_all_inline_media Bool
    :screen_name Str}))

(defalias Status
  (HMap
   :mandatory
   {:coordinates (Option Coordinates),
    :favorited Bool
    :truncated Bool
    :created_at Str
    :id_str Str
    :entities Entities,
    :in_reply_to_user_id_str (Option Str)
    :contributors (Vec Int)
    :text Str
    :retweet_count Int
    :in_reply_to_status_id_str (Option Str)
    :id Int
    :geo (Option Coordinates) ;; deprecated
    :retweeted Bool
    :possibly_sensitive Bool
    :in_reply_to_user_id (Option Int)
    :place (Option (Map Keyword Any)) ;; TODO: implement
    :user UserAbbrev,
    :in_reply_to_screen_name (Option Str)
    :source Str
    :in_reply_to_status_id (Option Int)}))

(defalias StatusAbbrev
  (HMap
   :mandatory
   {:coordinates (Option Coordinates)
    :favorited Bool
    :truncated Bool
    :created_at Str
    :retweeted_status (Option (HMap :mandatory
                                    {:coordinates (Option Coordinates)
                                     :favorited Bool
                                     :truncated Bool
                                     :created_at Str
                                     :id_str Str
                                     :entities Entities,
                                     :in_reply_to_user_id_str (Option Str)
                                     :contributors (Option (Vec Any)) ;;whatevs
                                     :text Str
                                     :retweet_count Int
                                     :in_reply_to_status_id_str (Option Str)
                                     :id Int
                                     :geo (Option Any) ;; deprecated
                                     :retweeted Bool
                                     :possibly_sensitive Bool
                                     :in_reply_to_user_id (Option Int)
                                     :place (Option (Map Keyword Any))
                                     :in_reply_to_screen_name (Option Str)
                                     :source Str
                                     :in_reply_to_status_id (Option Int)})),
    :id_str Str
    :entities Entities,
    :in_reply_to_user_id_str (Option Str)
    :contributors (Option Any) ;; no idea
    :text Str
    :retweet_count Int
    :in_reply_to_status_id_str (Option Str)
    :id Int
    :geo (Option (Map Keyword Any)) ;; whatevs
    :retweeted Bool
    :possibly_sensitive Bool
    :in_reply_to_user_id (Option Int)
    :place (Option (Map Keyword Any)) ;; probably not right
    :in_reply_to_screen_name (Option Str)
    :source Str
    :in_reply_to_status_id (Option Int)}))

(defalias User
  (HMap
   :mandatory
   {:profile_sidebar_fill_color Str
    :profile_sidebar_border_color Str
    :profile_background_tile Bool
    :name Str
    :profile_image_url Str
    :created_at Str
    :location Str
    :follow_request_sent Bool
    :profile_link_color Str
    :is_translator Bool
    :id_str Str
    :default_profile Bool
    :contributors_enabled Bool
    :favourites_count Int
    :url (Option Str)
    :profile_image_url_https Str
    :utc_offset Int
    :id Int
    :profile_use_background_image Bool
    :listed_count Int
    :profile_text_color Str
    :lang Str
    :followers_count Int
    :protected Bool
    :notifications Bool
    :profile_background_image_url_https Str
    :profile_background_color Str
    :verified Bool
    :geo_enabled Bool
    :time_zone Str
    :description Str
    :default_profile_image Bool
    :profile_background_image_url Str
    :status StatusAbbrev,
    :statuses_count Int
    :friends_count Int
    :following Bool
    :show_all_inline_media Bool
    :screen_name Str}))

