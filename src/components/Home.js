import React from 'react'
import { Button, Card, List, Tabs, Tooltip } from 'antd'
import { StarOutlined, StarFilled } from '@ant-design/icons'
const { TabPane } = Tabs
const tabKeys = { Videos: 'videos', Clips: 'clips', Streams: 'stream' }
const processUrl = url =>
	url.replace('%{height}', '252').replace('%{width}', '480').replace('{height}', '252').replace('{width}', '480')
const renderCardTitle = (item, loggedIn, favs = []) => {
	const title = `${item.broadcaster_name} - ${item.title}`
	const isFav = favs.find(fav => fav.twitch_id === item.twitch_id)
	return (
		<>
			{loggedIn &&
				<Tooltip title={isFav ? 'Remove from favorite list' : 'Add to favorite list'}>
					<Button shape='circle' icon={isFav ? <StarFilled /> : <StarOutlined />} />
				</Tooltip>
			}
			<div style={{ overflow: 'hidden', textOverflow: 'ellipsis', width: 450 }}>
				<Tooltip title={title}>
					<span>{title}</span>
				</Tooltip>
			</div>
		</>
	)
}
const renderCardGrid = (data, loggedIn, favs) => {
	return (
		<List
			grid={{ xs: 1, sm: 2, md: 4, lg: 4, xl: 6 }}
			dataSource={data}
			renderItem={item => (
				<List.Item style={{ marginRight: '20px' }}>
					<Card title={renderCardTitle(item, loggedIn, favs)}>
						<a href={item.url} target='_blank' rel='noopener noreferrer'>
							<img alt='Thumbnail' src={processUrl(item.thumbnail_url)} />
						</a>
					</Card>
				</List.Item>
			)}
		/>
	)
}
const Home = ({ resources, loggedIn, favoriteItems }) => {
	const { videos, clips, streams } = resources
	const { videos: favVideos, clips: favClips, streams: favStreams } = favoriteItems
	return (
		<Tabs defaultActiveKey={tabKeys.Videos}>
			<TabPane tab='Videos' key={tabKeys.Videos} forceRender={true}>
				{renderCardGrid(videos, loggedIn, favVideos)}
			</TabPane>
			<TabPane tab='Clips' key={tabKeys.Clips} forceRender={true}>
				{renderCardGrid(clips, loggedIn, favClips)}
			</TabPane>
			<TabPane tab='Streams' key={tabKeys.Streams} forceRender={true}>
				{renderCardGrid(streams, loggedIn, favStreams)}
			</TabPane>
		</Tabs>
	)
}
export default Home
