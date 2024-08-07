import React, {useEffect, useState} from 'react';
import {useNavigate} from 'react-router-dom';
import Table from './Table';
import {
    AiOutlineEye,
    AiOutlineEdit,
    AiFillStar,
    AiOutlineStar,
    AiOutlineSearch,
} from 'react-icons/ai';
import './TableContainer.css';
import ConfirmationModal from "./ConfirmationModal";
import {careerCompassApi} from "../Utils/CareerCompassApi";
import {urlPaths} from "../../Constants";
import {IoMdArchive} from "react-icons/io";
import Select from 'react-select';


const TableContainer = () => {
    const navigate = useNavigate();
    const [tags, setTags] = useState([]);
    const [showSearchBar, setShowSearchBar] = useState(false);
    const [showDeleteModal, setShowDeleteModal] = useState(false);
    const [selectedRowData, setSelectedRowData] = useState(null);
    const [data, setData] = useState([]);
    const [user, setUser] = useState(null);
    const [filteredData, setFilteredData] = useState([]);
    const existingTags = JSON.parse(localStorage.getItem('allTags'))
    const allTags=existingTags.map(tag => tag.name);

    useEffect(() => {
        const storedUser = JSON.parse(localStorage.getItem('userDetails'))
        setUser(storedUser)
        const userJson = JSON.parse(localStorage.getItem('user'))
        const unArchivedJobs = JSON.parse(localStorage.getItem('unArchivedJobs'));
        setData(unArchivedJobs);
    }, []);

    const handleStar = async (row) => {
        const storedUser = JSON.parse(localStorage.getItem('userDetails'))
        const userJson = JSON.parse(localStorage.getItem('user'))
        const response = await careerCompassApi.patchApiCall(userJson,
            urlPaths.UPDATE_STAR+storedUser.userId+`/${row.id}`);
        const allData = data.map((item) => {
            if (item.id === row.id) {
                return {...item, starred: !item.starred};
            }
            return item;
        });
        localStorage.setItem('unArchivedJobs', JSON.stringify(allData));
        setData(allData);
    };


    const columns = [
        {
            Header: 'Starred',
            accessor: 'starred',
            Cell: ({row}) => (
                <div>
                    {row.original.starred ? <AiFillStar className="action-icon action-star" onClick={() => handleStar(row.original)}/> :
                        <AiOutlineStar className="action-icon" onClick={() => handleStar(row.original)}/>}
                </div>
            )
        },
        {
            Header: 'Company Name',
            accessor: 'companyUrl',
            Cell: ({row}) => (
                <a href={row.original.companyUrl} target="_blank" rel="noopener noreferrer">
                    {row.original.company}
                </a>
            )
        },
        {Header: 'Role', accessor: 'position'},
        {Header: 'Applied On', accessor: 'applicationDate'},
        {Header: 'Status', accessor: 'status'},
        {
            Header: 'Tags',
            accessor: 'jobTags',
            Cell: ({value}) => (
                <div className="tags-cell">
                    {value.map((tag, index) => (
                        <span key={index} className={`added-tag tag-${index % 4}`}>
              {tag.name}
            </span>
                    ))}
                </div>
            ),
        },
        {
            Header: 'Actions',
            accessor: 'actions',
            Cell: ({row}) => (
                <>
                    <AiOutlineEye onClick={() => handleView(row.original)} className="action-icon"/>
                    <AiOutlineEdit onClick={() => handleEdit(row.original)} className="action-icon"/>
                    <IoMdArchive onClick={() => handleDelete(row.original)} className="action-icon"/>
                </>
            )
        }
    ];

    const handleView = (rowData) => {
        navigate('/details', {state: {rowData}});
    };

    const handleEdit = (rowData) => {
        navigate('/edit', {state: {rowData}});
    };

    const handleDelete = (rowData) => {
        setSelectedRowData(rowData);
        setShowDeleteModal(true);
    };

    const confirmDelete = async () => {
        try {
            const storedUser = JSON.parse(localStorage.getItem('userDetails'))
            const userJson = JSON.parse(localStorage.getItem('user'))
            const response = await careerCompassApi.deleteApiCall(userJson, urlPaths.ARCHIVE_JOB_APPLICATION + storedUser.userId + `/${selectedRowData.id}`);
            const updatedData = data.filter(item => item.id !== selectedRowData.id);
            localStorage.setItem('unArchivedJobs', JSON.stringify(updatedData));
            setData(updatedData);
        } catch (error) {
            console.error('Error fetching data:', error);
        }
        setShowDeleteModal(false);
    };


    const handleChange = (newTags) => {
        setTags(newTags);
    };

    const toggleSearchBar = () => {
        setShowSearchBar(!showSearchBar);
    };

    useEffect(() => {
        const filtered = tags.length > 0
            ? data.filter((item) => tags.some((tag) => item.jobTags.map(tagObj => tagObj.name).includes(tag)))
            : data;
        setFilteredData(filtered);
    }, [tags, data]);

    return (
    <div className="search-table-container">
            <div className="search-container">
            <button className="search-button" onClick={toggleSearchBar}>
                <AiOutlineSearch className="search-icon"/>
                <span className="search-text">Search by Tags</span>
            </button>
                {showSearchBar && (
                    <div className="search-bar">
                        <Select
                            isMulti
                            options={allTags.map((tag) => ({ value: tag, label: tag }))}
                            value={tags.map((tag) => ({ value: tag, label: tag }))}
                            onChange={(selectedOptions) => handleChange(selectedOptions.map((option) => option.value))}
                            placeholder="Type a tag and press enter..."
                            className="react-select"
                        />
                    </div>
                )}
        </div>
        <div className="table-container">
                <Table data={filteredData} columns={columns}
                       iconStyle={{fontSize: '24px', marginRight: '12px'}}/>
        </div>
        <ConfirmationModal
            show={showDeleteModal}
            onHide={() => setShowDeleteModal(false)}
            onConfirm={confirmDelete}
            rowData={selectedRowData}
            bodyContent={
                <>
                    <p>Are you sure you want to Archive this job application?</p>
                    {selectedRowData && (
                        <div>
                            <p>
                                <strong>Job URL: </strong>
                                <a href={selectedRowData.companyUrl} target="_blank"
                                   rel="noopener noreferrer">
                                    {selectedRowData.company}
                                </a>
                            </p>
                            <p><strong>Role: </strong> {selectedRowData.position}</p>
                            <p><strong>Status : </strong> {selectedRowData.status}</p>
                        </div>
                    )}
                </>
            }
        />
    </div>
    );
};

export default TableContainer;