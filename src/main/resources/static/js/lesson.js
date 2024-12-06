function completeLesson(lessonId) {
    const lessonIdNum = Number(lessonId);  // lessonIdを数値に変換
    const url = `/completeLesson/${lessonIdNum}`; // 正しいURLを構築
    console.log("Fetch URL:", url); // デバッグ用

    fetch(url, { method: 'POST' })
        .then(response => {
            if (!response.ok) {
                throw new Error('ネットワークエラー');
            }
            return response.json();
        })
        .then(data => {
            if (data.success) {
                parent.updateExperiencePoints(data.updatedExperiencePoints);
                
                 // サイドメニューを更新
                const completedIds = data.completedContentIds;
            document.querySelectorAll('.cp_sidebar_nav li').forEach(item => {
                const contentId = parseInt(item.dataset.id, 10); // サイドメニューに設定されたIDを取得
                if (completedIds.includes(contentId)) {
                    // 完了マークを付ける
                    item.querySelector('.status').textContent = "完了";
                    }
                    });

            } else {
                alert("レッスン完了に失敗しました。");
            }
        })
        .catch(error => {
            console.error(error);
            alert("ネットワークエラーが発生しました。");
        });
}
